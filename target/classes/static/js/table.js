function full_name(user) {
	return `${user.surname} ${user.name}`;
}

function get_subjects() {
	return new Promise((resolve, reject) => {
		$.get(url + "/getSubjects", function (subjects) {
			console.log(subjects)
			resolve(subjects)
		});

	});
}

function get_students(subject) {
	return new Promise((resolve, reject) => {
		let subjectName = subject_data.name
		console.log(subject)
		$.ajax({
			url: url + "/getStudentsInfoBySubject",
			method: "post",
			data: {"subjectName": subject.name},
			error: function (message) {
				console.log(message);
			},
			success: function (students) {
				resolve(students);
				console.log(students)
			}
		});

	});
}

function process_students(students) {
	return students.map(
		student => ({
			id: student.id,
			login: student.login,
			name: full_name(student),
			marks: student.marks
				.map(
					mark => ({
						mark: mark.mark,
						date: convertDateToUTC(new Date(mark.date + ' 00:00')) ,
					})
				),
			visits: student.visits
				.map(
					visit => ({
						visit: visit.visit,
						date: convertDateToUTC(new Date(visit.date + ' 00:00')),
					})
				)
		})
	);
}

function all_mark_dates(students) {
	let dates = [];

	for (student of students) {
		for (mark of student.marks) {
			dates.push(mark.date);
		}
	}

	return dates.filter(
		(date, i, self) =>
			self.findIndex(
				d => d.getTime() === date.getTime()
			) === i
	);
}

function all_visit_dates(students) {
	let dates = [];

	for (student of students) {
		for (visit of student.visits) {
			dates.push(visit.date);
		}
	}

	return dates.filter(
		(date, i, self) =>
			self.findIndex(
				d => d.getTime() === date.getTime()
			) === i
	);
}

function process_marks(students, dates) {
	for (student of students) {
		const marks = new Map(
			student.marks
				.map(mark => [
					mark.date.getTime(),
					mark.mark,
				])
		);

		student.marks = new Map(
			dates.map(date => [
				date,
				marks.get(date.getTime())
			])
		);
	}
}

function process_visits(students, dates) {
	for (student of students) {
		const visits = new Map(
			student.visits
				.map(visit => [
					visit.date.getTime(),
					visit.visit,
				])
		);

		student.visits = new Map(
			dates.map(date => [
				date,
				visits.get(date.getTime())
			])
		);
	}
}

function save_changes(subject, mark_changes, visit_changes) {


	let marks = Array.from(mark_changes)
			.map(([x, y]) => ([JSON.parse(x), y]))
			.map(
				([[student_id, date], value]) => ({ student_id, date, value })
			)
	let visits = Array.from(visit_changes)
			.map(([x, y]) => ([JSON.parse(x), y]))
			.map(
				([[student_id, date], value]) => ({ student_id, date, value })
			)
console.log(marks)
	var jsonMarks = JSON.stringify(marks);
	var jsonVisits = JSON.stringify(visits);
	$.ajax({
		url: url + "/updateMarkTable",
		dataType: "json",
		method: "post",
		data: {"marksJson":jsonMarks, "subject": subject.name},
		error: function (message) {
			console.log(message);
		},
		success: function () {
			console.log("marks OK")
		}
	});
	$.ajax({
		url: url + "/updateVisitTable",
		dataType: "json",
		method: "post",
		data: {"visits": jsonVisits, "subject": subject.name},
		error: function (message) {
			console.log(message);
		},
		success: function () {
			console.log("visits OK")
		}
	});


}

async function make_subject_table(subject, read_only = false) {


	const raw_students = await get_students(subject);
	const students = process_students(raw_students);

	const mark_dates = all_mark_dates(students);
	const visit_dates = all_visit_dates(students);

	process_marks(students, mark_dates);
	process_visits(students, visit_dates);

	console.log("students", students);

	const changed_marks = new Map();
	const changed_visits = new Map();

	const sum_update_target = new EventTarget();
	const visit_update_target = new EventTarget();
	const sum_update = new Event('sum_update');

	function student_data_popup(id) {
		const student = raw_students.find(s => s.id === id);
		return popup({
			children: [
				div({
					children: [text(`Прізвище: ${student.surname}`)]
				}),
				div({
					children: [text(`Ім'я: ${student.name}`)]
				}),
				div({
					children: [text(`Дата народження: ${(new Date(student.birthday)).toLocaleDateString()}`)]
				}),
				div({
					children: [text(`Стать: ${student.sex == 'male' ? 'чоловіча' : 'жіноча'}`)]
				}),
				div({
					children: [text(`Адреса: ${student.address}`)]
				}),
				div({
					children: [text(`Логін: ${student.login}`)]
				}),
				div({
					children: [text(`Пароль: ${student.password}`)]
				}),
			]
		})
	}

	return div({
		id: "studentMarksTableContainer",
		children: [
			h1({
				id: "selectedSubjectTitle",
				text: subject.name
			}),
			h2({
				id: "selectedSubjectSubtitle",
				text: `Викладач: ${full_name(subject.teacher)}`
			}),
			divider(),
			h3({
				text: `Бали`
			}),
			table({
				id: "studentMarksTable",
				children: [
					thead({
						children: [
							table_row({
								children: [
									table_cell({ text: "Ім'я студента" }),
									...mark_dates.map(date =>
										table_cell({ text: date.toLocaleDateString() })
									),
									table_cell({ text: "Сума балів" })
								]
							})
						]
					}),
					tbody({
						children: students.map(student =>
							table_row({
								children: [
									event_listener({
										events: [['click', () => new Popup(student_data_popup(student.id))]],
										child: table_cell({ text: student.name })
									}),
									...mark_dates.map(date =>
										table_cell_editable({
											text: student.marks.get(date),
											on_edit: (new_value) => {
												const new_mark = parseInt(new_value, 10);
												student.marks.set(date, new_mark);
												changed_marks.set(JSON.stringify([student.id, date]), new_mark);
												sum_update_target.dispatchEvent(sum_update);
											},
											editable: !read_only
										})
									),
									(() => {
										const sum_marks = (marks) => Array.from(
											marks.values()
										)
											.map(mark => mark || 0)
											.reduce(
												(sum = 0, v) => sum + v
											) || 0;

										const cell = table_cell({
											text: sum_marks(student.marks)
										});
										sum_update_target.addEventListener("sum_update", () => {
											cell.innerText = sum_marks(student.marks);
										});
										return cell;
									})()
								]
							})
						)
					})
				]
			}),
			h3({
				text: `Відвідування`
			}),
			table({
				id: "studentVisitTable",
				children: [
					thead({
						children: [
							table_row({
								children: [
									table_cell({ text: "Ім'я студента" }),
									...visit_dates.map(date =>
										table_cell({ text: date.toLocaleDateString() })
									),
									table_cell({ text: "Всього відвідувань" })
								]
							})
						]
					}),
					tbody({
						children: students.map(student =>
							table_row({
								children: [
									event_listener({
										events: [['click', () => new Popup(student_data_popup(student.id))]],
										child: table_cell({ text: student.name })
									}),
									...visit_dates.map(date =>
										table_cell_editable({
											text: student.visits.get(date),
											on_edit: (new_value) => {
												console.info(JSON.stringify(new_value));
												new_value = (new_value === "true");
												student.visits.set(date, new_value);
												changed_visits.set(JSON.stringify([student.id, date]), new_value);
												visit_update_target.dispatchEvent(sum_update);
											},
											editable: !read_only
										})
									),
									(() => {
										const sum_visits = (visits) => Array.from(
											visits.values()
										)
											.map(visited => visited ? 1 : 0)
											.reduce(
												(sum, v) => sum + v
											) || 0;

										const cell = table_cell({
											text: sum_visits(student.visits)
										});
										visit_update_target.addEventListener("sum_update", () => {
											cell.innerText = sum_visits(student.visits);
										});
										return cell;
									})()
								]
							})
						)
					})
				]
			}),
			divider(),
			div({
				id: "bottomButtons",
				children: [
					button({
						text: "Зберегти",
						text_clicked: "Збережено",
						on_click: () => {
							save_changes(subject, changed_marks, changed_visits);
							changed_marks.clear();
							changed_visits.clear();
						},
						hidden: !!read_only
					})
				]
			})
		]
	});
}

class Popup {
	constructor(popup) {
		this.close();
		document.body.append(popup);
	}

	close() {
		Popup.close();
	}

	static close() {
		document
			.querySelectorAll('body > #popup')
			.forEach(
				elem => elem.parentNode.removeChild(elem)
			);
	}
}

function popup({ children } = {}) {
	return div({
		id: 'popup',
		class_list: ['popup-body'],
		children: [
			event_listener({
				events: [['click', Popup.close]],
				child: div({
					class_list: ['grayout'],
				})
			}),
			div({
				class_list: ['popup-wrap'],
				children: [
					div({
						class_list: ['popup', 'page-block', 'shadow-5'],
						children: [
							div({
								class_list: ['popup-inner'],
								children: children
							}),
							event_listener({
								events: [['click', Popup.close]],
								child: div({
									class_list: ['close-wrap'],
									children: [
										div({
											class_list: ['close']
										})
									]
								})
							})
						]
					})
				]
			})
		]
	});
}

function event_listener({ events, child }) {
	for ([event, callback] of events) {
		child.addEventListener(event, callback);
	}
	return child;
}

function text(string = '') {
	return document.createTextNode(string);
}

function div({ id, children, class_list } = {}) {
	const element = document.createElement('div');
	if (id) element.id = id;
	if (children) element.append(...children);
	if (class_list) element.classList.add(...class_list);
	return element;
}

function h1({ id, text } = {}) {
	const element = document.createElement('h1');
	if (id) element.id = id;
	if (text !== undefined) element.innerText = text;
	return element;
}

function h2({ id, text } = {}) {
	const element = document.createElement('h2');
	if (id) element.id = id;
	if (text !== undefined) element.innerText = text;
	return element;
}

function h3({ id, text } = {}) {
	const element = document.createElement('h3');
	if (id) element.id = id;
	if (text !== undefined) element.innerText = text;
	return element;
}

function divider() {
	const element = document.createElement('divider');
	return element;
}

function table({ id, children } = {}) {
	const element = document.createElement('table');
	if (id) element.id = id;
	if (children) element.append(...children);
	return element;
}

function thead({ id, children } = {}) {
	const element = document.createElement('thead');
	if (id) element.id = id;
	if (children) element.append(...children);
	return element;
}

function tbody({ id, children } = {}) {
	const element = document.createElement('tbody');
	if (id) element.id = id;
	if (children) element.append(...children);
	return element;
}

function table_row({ children } = {}) {
	const element = document.createElement('tr');
	if (children) element.append(...children);
	return element;
}

function table_cell({ text, children } = {}) {
	const element = document.createElement('td');
	if (text !== undefined) element.innerText = text;
	if (children) element.append(...children);
	return element;
}

function table_cell_editable({ text, on_edit, editable } = {}) {
	const element = document.createElement('td');
	if (text !== undefined) element.innerText = text;
	element.contentEditable = !!editable;
	if (on_edit) {
		element.addEventListener("input", () => {
			on_edit(element.innerText.replace(/\s+$/, ''));
		});
	}
	return element;
}

function button({ text, on_click, text_clicked, hidden = false } = {}) {
	const element = document.createElement('button');
	if (text !== undefined) element.innerText = text;
	if (on_click) {
		element.onclick = () => {
			if (text_clicked) element.innerText = text_clicked;
			setTimeout(() => {
				if (text) element.innerText = text;
			}, 3000);
			on_click();
		}
	} else {
		element.disabled = true
	};

	element.style.visibility = hidden ? 'collapse' : 'visible';

	return element;
}

function convertDateToUTC(date) {
	return new Date(date.getUTCFullYear(), date.getUTCMonth(),
		date.getUTCDate(), date.getUTCHours(), date.getUTCMinutes(), date.getUTCSeconds());
}