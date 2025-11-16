import React, { useState } from "react";
import "./EditModal.css";

export default function EditModal({ task, onClose, onSave }) {
  const [title, setTitle] = useState(task.title);
  const [description, setDescription] = useState(task.description);

  return (
    <div className="modal-overlay">
      <div className="modal-box">
        <h2>Edit Task</h2>

        <input
          className="modal-input"
          type="text"
          placeholder="Task title"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
        />

        {/* Updated to text input instead of textarea */}
        <input
          className="modal-input"
          type="text"
          placeholder="Short description"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
        />

        <div className="modal-buttons">
          <button className="cancel-btn" onClick={onClose}>Cancel</button>
          <button
  className="save-btn"
  onClick={() => onSave(title, description)}
>
  Save
</button>

        </div>
      </div>
    </div>
  );
}
