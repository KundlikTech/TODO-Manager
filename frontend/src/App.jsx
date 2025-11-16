import React, { useState, useEffect } from "react";
import TaskList from "./components/TaskList";
import TaskForm from "./components/TaskForm";
import "./App.css";

function App() {
  const [reload, setReload] = useState(false);
  const [dark, setDark] = useState(false);

  // Persist theme in localStorage
  useEffect(() => {
    const stored = localStorage.getItem("darkMode");
    if (stored === "true") setDark(true);
  }, []);

  useEffect(() => {
    localStorage.setItem("darkMode", dark ? "true" : "false");
  }, [dark]);

  return (
    <div className={`app-root ${dark ? "dark" : "light"}`}>
      <div className="container">
        <header className="app-header">
          <h1>TODO App</h1>

          <div className="header-right">
            <div className="theme-toggle">
              <label className="switch">
                <input
                  type="checkbox"
                  checked={dark}
                  onChange={() => setDark(!dark)}
                />
                <span className="slider" />
              </label>
              <span className="toggle-label">{dark ? "Dark" : "Light"}</span>
            </div>
          </div>
        </header>

        <TaskForm onTaskAdded={() => setReload(!reload)} />
        <TaskList reload={reload} />
      </div>
    </div>
  );
}

export default App;
