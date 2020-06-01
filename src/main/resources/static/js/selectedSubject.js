const url = 'http://localhost:8081';
const query_string = window.location.search;
const url_parameters = new URLSearchParams(query_string);
const subject_name = url_parameters.get('name') || 'Math'
const subject_data = new Promise((resolve, reject) => {
	$.ajax({
		url: url + "/getSubject",
		method: "post",
		data: {"name":subject_name},
		error: reject,
		success: resolve
	})
});

const read_only = false;

window.addEventListener('DOMContentLoaded', async () => {
	const subject = await subject_data
	document.title = `${subject.name} | ${full_name(subject.teacher)}`;
	document.body.appendChild(
		await make_subject_table(subject, read_only)
	);
});