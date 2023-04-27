const editButtons = Array.from(document.querySelectorAll('.edit-todo'));
editButtons.forEach(button =>
    button.addEventListener('click', edit));


function edit(evt) {
    const row = evt.target.closest(".task-row");
    let taskDescriptionElement = row.querySelector(".task__description");
    const textDescription = taskDescriptionElement.textContent;
    let taskStatus = row.querySelector(".task__status");
    const textStatus = taskStatus.textContent;

    taskDescriptionElement.outerHTML = `<td><input type="text" class="edit-task__description" value="${textDescription}"></td>`;
    taskStatus.outerHTML =
        `<td>
            <select class="edit-task__status" value="${textStatus}">
                <option>IN_PROGRESS</option>
                <option>DONE</option>
                <option>PAUSED</option>
            </select>
         </td>`;

    const cellWithControls = getControls();
    row.querySelector('.edit-todo').outerHTML = cellWithControls.outerHTML;

}

function getControls() {
    const cell = document.createElement('td');
    const buttonSave = document.createElement('button');
    buttonSave.type = 'button';
    buttonSave.textContent = 'Save';

    buttonSave.classList.add('edit-task__button_type_save');
    buttonSave.addEventListener('click', saveEditTask);
    document.querySelectorAll('tr').forEach(row => {
        row.addEventListener('click', function (evt) {
            if (evt.target.classList.contains('edit-task__button_type_save')) {
                const dataTask = getDataTask(evt);
                saveEditTask(dataTask);
            }
        });
    })

    const buttonCancel = document.createElement('button');
    buttonCancel.textContent = 'Cancel';
    buttonCancel.classList.add('edit-task__button_type_cancel')
    cell.append(buttonSave, buttonCancel);
    return cell;
}

function getDataTask(evt) {
    const row = evt.target.closest(".task-row");
    const id = row.querySelector(".task__id").textContent;
    const description = row.querySelector(".edit-task__description").value;
    const status = row.querySelector(".edit-task__status").value;
    return {id, description, status};
}

function saveEditTask(task) {
    fetch(`/${task.id}`, {
        method: 'PATCH',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(task)
    }).catch(console.log)

}
