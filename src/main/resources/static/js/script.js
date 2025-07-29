let tasksCache = [];
const taskListContainer = document.getElementById('taskList');
const taskListTitleElement = document.getElementById('taskListTitle');

function renderTasks(tasks, title) {
    taskListTitleElement.innerHTML = title;
    taskListContainer.innerHTML = '';

    if (tasks.length === 0) {
        taskListContainer.innerHTML = '<p>No tasks found.</p>';
        return;
    }

    tasks.forEach(task => {
        const taskElement = document.createElement('div');
        taskElement.classList.add('task', `priority-${task.priority.toLowerCase()}`);

        const titleClass = task.status === 'COMPLETED' ? 'task-title-complete' : '';

        taskElement.innerHTML = `
            <h3 class="${titleClass}">${task.title}</h3>
            <p>${task.description}</p>
            <div class="details">
                <span class="status-badge status-${task.status.toLowerCase()}">${task.status.replace('_', ' ')}</span>
            </div>
            <div class="actions">
                <button onclick="openEditModal(${task.id})">Edit</button>
                <button onclick="deleteTask(${task.id})">Delete</button>
            </div>
        `;
        taskListContainer.appendChild(taskElement);
    });
}

function fetchAndDisplayTasks() {
    fetch('/tasks')
        .then(response => response.json())
        .then(tasks => {
            tasksCache = tasks.sort((a,b) => a.id - b.id);
            renderTasks(tasks, 'Task List');
        });
}

document.getElementById('searchForm').addEventListener('submit', function(event) {
    event.preventDefault();
    const searchTerm = document.getElementById('searchInput').value.trim();

    if (searchTerm) {
        fetch(`/tasks/${searchTerm}`)
            .then(response => response.json())
            .then(tasks => {
                const title = `Results for: "${searchTerm}" <button onclick="fetchAndDisplayTasks()">View all</button>`;
                renderTasks(tasks, title);
            });
    }
});

document.getElementById('taskForm').addEventListener('submit', function(event) {
    event.preventDefault();
    const taskRequest = {
        title: document.getElementById('title').value,
        description: document.getElementById('description').value,
        status: document.getElementById('status').value,
        priority: document.getElementById('priority').value
    };
    fetch('/tasks', { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(taskRequest) })
    .then(response => {
        if (response.status === 201) {
            document.getElementById('taskForm').reset();
            closeAddTaskModal();
            fetchAndDisplayTasks();
        } else {
            alert('Error creating task.');
        }
    });
});

document.getElementById('editForm').addEventListener('submit', function(event) {
    event.preventDefault();
    const id = document.getElementById('editTaskId').value;
    const taskRequest = {
        title: document.getElementById('editTitle').value,
        description: document.getElementById('editDescription').value,
        status: document.getElementById('editStatus').value,
        priority: document.getElementById('editPriority').value
    };
    fetch(`/tasks/${id}`, { method: 'PUT', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(taskRequest) })
    .then(response => { if(response.ok) { closeEditModal(); fetchAndDisplayTasks(); } else { alert('Error updating task.'); } });
});

function deleteTask(id) {
    if (confirm('Are you sure you want to delete this task?')) {
        fetch(`/tasks/${id}`, { method: 'DELETE' }).then(response => { if (response.status === 204) { fetchAndDisplayTasks(); } else { alert('Error deleting task.'); } });
    }
}

function openAddTaskModal() {
    document.getElementById('addTaskModal').style.display = 'flex';
}

function closeAddTaskModal() {
    document.getElementById('addTaskModal').style.display = 'none';
}

function openEditModal(id) {
    const taskToEdit = tasksCache.find(task => task.id === id);
    if (!taskToEdit) return;
    document.getElementById('editTaskId').value = taskToEdit.id;
    document.getElementById('editTitle').value = taskToEdit.title;
    document.getElementById('editDescription').value = taskToEdit.description;
    document.getElementById('editStatus').value = taskToEdit.status;
    document.getElementById('editPriority').value = taskToEdit.priority;
    document.getElementById('editModal').style.display = 'flex';
}

function closeEditModal() { document.getElementById('editModal').style.display = 'none'; }

document.addEventListener('DOMContentLoaded', fetchAndDisplayTasks);