import React, { useEffect, useState } from "react";
import "./Toast.css";

export default function Toast({ message, type = "success", show }) {
  const [visible, setVisible] = useState(show);

  useEffect(() => {
    setVisible(show);
    if (show) {
      const timer = setTimeout(() => setVisible(false), 2500);
      return () => clearTimeout(timer);
    }
  }, [show]);

  return (
    <div className={`toast ${visible ? "show" : ""} ${type}`}>
      {message}
    </div>
  );
}
