const url = 'http://localhost:8081';
let selectedSubject;

window.addEventListener('load', (event) => {
    subjectsPrint();
});

function subjectsPrint() {
    $.get(url + "/getSubjects", function (subjects) {

        document.querySelector('#subjects tbody').innerHTML += subjects.map(n => `
  <tr onclick="selectSubject(${n.name})">
    <td >${n.name}</td>
    <td >${n.teacher.name} ${n.teacher.surname}</td>
  </tr>
`).join('');
    });

}

function selectSubject(subjectName) {
    $.ajax({
        url: url + "/getSubject",
        method: "post",
        data: subjectName,
        error: function (message) {
            console.log(message)
        },
        success: function (subject) {
            selectedSubject = subject

        }
    }).then(document.location.href = url + "/selectedSubject")


}