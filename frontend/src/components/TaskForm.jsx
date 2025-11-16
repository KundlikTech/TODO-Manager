import React, { useState } from "react";
import "./TaskForm.css";

export default function TaskForm({ onTaskAdded }) {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [saving, setSaving] = useState(false);

  const addTask = async (e) => {
    e.preventDefault();
    if (!title) return alert("Title required");

    try {
      setSaving(true);
      await fetch("http://localhost:4567/tasks", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ title, description, status: "pending" }),
      });

      setTitle("");
      setDescription("");

      onTaskAdded();
    } catch (err) {
      console.error(err);
      alert("Failed to add task");
    } finally {
      setSaving(false);
    }
  };

  return (
    <form onSubmit={addTask} className="task-form">
      <div className="form-row">
        <input
          className="input-title"
          type="text"
          placeholder="Task title"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
        />

        <button className="btn-primary" type="submit" disabled={saving}>
          {saving ? "Adding..." : "Add"}
        </button>
      </div>

      <input
        className="input-desc"
        type="text"
        placeholder="Short description"
        value={description}
        onChange={(e) => setDescription(e.target.value)}
      />
    </form>
  );
}
