import React, { useEffect, useState } from "react";
import EditModal from "./EditModal";
import Toast from "./Toast";
import {
  DragDropContext,
  Droppable,
  Draggable
} from "@hello-pangea/dnd";
import "./TaskList.css";

export default function TaskList({ reload }) {
  const [tasks, setTasks] = useState([]);
  const [editingTask, setEditingTask] = useState(null);
  const [toast, setToast] = useState({ show: false, message: "", type: "success" });

  const showToast = (msg, type = "success") => {
    setToast({ show: true, message: msg, type });
    setTimeout(() => setToast({ ...toast, show: false }), 2500);
  };

  const fetchTasks = async () => {
    try {
      const res = await fetch("http://localhost:4567/tasks");
      const data = await res.json();
      setTasks(data);
    } catch (error) {
      showToast("Failed to load tasks", "error");
    }
  };

  useEffect(() => {
    fetchTasks();
  }, [reload]);

  const cycleStatus = async (task) => {
    const statusOrder = ["pending", "in-progress", "completed"];
    const next = statusOrder[(statusOrder.indexOf(task.status) + 1) % 3];

    try {
      await fetch(`http://localhost:4567/tasks/${task.id}/status`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ status: next })
      });

      setTasks((prev) =>
        prev.map((t) => (t.id === task.id ? { ...t, status: next } : t))
      );

      showToast(`Status changed to ${next}`, "success");
    } catch {
      showToast("Failed to update status", "error");
    }
  };

  // ✅ DELETE WITHOUT PROMPT
  const deleteTask = async (id) => {
    try {
      await fetch(`http://localhost:4567/tasks/${id}`, { method: "DELETE" });

      setTasks((prev) => prev.filter((t) => t.id !== id));
      showToast("Task deleted");
    } catch {
      showToast("Failed to delete task", "error");
    }
  };

  const onDragEnd = (result) => {
    if (!result.destination) return;

    const items = Array.from(tasks);
    const [moved] = items.splice(result.source.index, 1);
    items.splice(result.destination.index, 0, moved);

    setTasks(items);
    showToast("Tasks reordered");
  };

  const badgeColor = (status) => {
    if (status === "completed") return "badge badge-completed";
    if (status === "in-progress") return "badge badge-progress";
    return "badge badge-pending";
  };

  return (
    <div className="tasklist-wrap">

      <Toast message={toast.message} type={toast.type} show={toast.show} />

      <DragDropContext onDragEnd={onDragEnd}>
        <Droppable droppableId="tasks">
          {(provided) => (
            <div {...provided.droppableProps} ref={provided.innerRef}>

              {tasks.map((t, index) => (
                <Draggable key={t.id} draggableId={String(t.id)} index={index}>
                  {(provided2) => (
                    <div
                      className="task-card"
                      ref={provided2.innerRef}
                      {...provided2.draggableProps}
                      {...provided2.dragHandleProps}
                    >
                      <div className="task-title">
                        {t.title}
                        <span
                          className={badgeColor(t.status)}
                          onClick={() => cycleStatus(t)}
                          style={{ cursor: "pointer" }}
                          title="Click to change status"
                        >
                          {t.status}
                        </span>
                      </div>

                      <div className="task-desc">{t.description}</div>

                      <div className="actions">
                        <button className="btn btn-edit" onClick={() => setEditingTask(t)}>
                          Edit
                        </button>
                        <button className="btn btn-delete" onClick={() => deleteTask(t.id)}>
                          Delete
                        </button>
                      </div>
                    </div>
                  )}
                </Draggable>
              ))}

              {provided.placeholder}
            </div>
          )}
        </Droppable>
      </DragDropContext>

      {editingTask && (
        <EditModal
          task={editingTask}
          onClose={() => setEditingTask(null)}
          onSave={async (title, desc) => {
            try {
              await fetch(`http://localhost:4567/tasks/${editingTask.id}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                  title,
                  description: desc,
                  status: editingTask.status     // 🔥 Keep current status
                })
              });

              showToast("Task updated");

              fetchTasks();
              setEditingTask(null);
            } catch {
              showToast("Update failed", "error");
            }
          }}
        />
      )}
    </div>
  );
}
