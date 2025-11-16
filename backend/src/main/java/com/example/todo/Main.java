package com.example.todo;

import static spark.Spark.*;

import com.example.todo.dao.TaskDAO;
import com.example.todo.model.Task;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        port(4567);

        TaskDAO dao = new TaskDAO();
        Gson gson = new Gson();

        // CORS preflight
        options("/*", (req, res) -> {
            String acrh = req.headers("Access-Control-Request-Headers");
            if (acrh != null) res.header("Access-Control-Allow-Headers", acrh);
            String acrm = req.headers("Access-Control-Request-Method");
            if (acrm != null) res.header("Access-Control-Allow-Methods", acrm);
            return "OK";
        });

        before((req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
            res.header("Access-Control-Allow-Headers", "Content-Type,Authorization");
            res.type("application/json");
        });

        // GET all
        get("/tasks", (req, res) -> {
            List<Task> all = dao.getAll();
            return gson.toJson(all);
        });

        // GET by id
        get("/tasks/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            Task t = dao.getById(id);
            if (t == null) {
                res.status(404);
                return "{}";
            }
            return gson.toJson(t);
        });

        // CREATE
        post("/tasks", (req, res) -> {
            try {
                Task t = gson.fromJson(req.body(), Task.class);
                if (t.getStatus() == null) t.setStatus("pending");
                int newId = dao.create(t);
                if (newId > 0) {
                    t.setId(newId);
                    res.status(201);
                    return gson.toJson(t);
                } else {
                    res.status(500);
                    return "{\"error\":\"insert failed\"}";
                }
            } catch (Exception e) {
                res.status(400);
                return "{\"error\":\"bad request\"}";
            }
        });

        // UPDATE whole task (title, description, status)
        put("/tasks/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            Task t = gson.fromJson(req.body(), Task.class);
            t.setId(id);
            boolean ok = dao.update(t);
            if (!ok) res.status(404);
            return gson.toJson(t);
        });

        // DELETE
        delete("/tasks/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            boolean ok = dao.delete(id);
            if (ok) return "{\"status\":\"deleted\"}";
            res.status(404);
            return "{}";
        });

        // UPDATE status only (accepts {"status":"..."} or Task JSON)
        put("/tasks/:id/status", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            try {
                // try parsing simple json
                JsonObject jo = gson.fromJson(req.body(), JsonObject.class);
                String status = null;
                if (jo != null && jo.has("status")) status = jo.get("status").getAsString();
                if (status == null) {
                    // fallback: parse into Task
                    Task t = gson.fromJson(req.body(), Task.class);
                    status = t.getStatus();
                }
                if (status == null) status = "pending";
                boolean ok = dao.updateStatus(id, status);
                if (!ok) {
                    res.status(404);
                    return "{\"error\":\"update failed\"}";
                }
                return "{\"message\":\"status updated\"}";
            } catch (Exception e) {
                res.status(400);
                return "{\"error\":\"bad request\"}";
            }
        });

        // health
        get("/health", (req, res) -> "{\"status\":\"ok\"}");
    }
}
