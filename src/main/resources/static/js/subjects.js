const url = 'http://localhost:8081';
function select_subject(subject_name) {
    window.location.assign(`/selectedSubject?name=${subject_name}`);
}

const subjects_promise = new Promise((resolve) => {
    $.get(url + "/getSubjects", function (subjects) {
        resolve(subjects)
    });

    // setTimeout(() => resolve(
    //     JSON.parse(`[{ "id": 3, "name": "Math", "teacher": { "id": 1, "login": "admin", "password": "admin", "name": "Alex", "surname": "Frolov", "address": "here", "sex": "male", "birthday": "2020/05/31", "role": "ADMIN", "marks": [] } }, { "id": 4, "name": "Biology", "teacher": { "id": 1, "login": "admin", "password": "admin", "name": "Alex", "surname": "Frolov", "address": "here", "sex": "male", "birthday": "2020/05/31", "role": "ADMIN", "marks": [] } }, { "id": 5, "name": "Programing", "teacher": { "id": 1, "login": "admin", "password": "admin", "name": "Alex", "surname": "Frolov", "address": "here", "sex": "male", "birthday": "2020/05/31", "role": "ADMIN", "marks": [] } }]`)
    // ), 500);
});

const read_only = false;

window.addEventListener('DOMContentLoaded', async () => {
    const subjects = await subjects_promise;
    document.body.append(
        div({
            id: "studentMarksTableContainer",
            children: [
                h1({
                    text: `Доступні предмети`
                }),
                divider(),
                table({
                    children: [
                        thead({
                            children: [
                                table_row({
                                    children: [
                                        table_cell({ text: "Назва предмета" }),
                                        table_cell({ text: "Викладач" })
                                    ]
                                })
                            ]
                        }),
                        tbody({
                            children: subjects.map(subject => {
                                const element = event_listener({
                                    events: [['click', () => select_subject(subject.name)]],
                                    child: table_row({
                                        children: [
                                            table_cell({ text: subject.name }),
                                            table_cell({ text: full_name(subject.teacher) }),
                                        ]
                                    })
                                })
                                element.style.cursor = 'pointer';
                                return element;
                            })
                        })
                    ]
                }),
                div({
                    id: "bottomButtons",
                })
            ]
        })
    );
});