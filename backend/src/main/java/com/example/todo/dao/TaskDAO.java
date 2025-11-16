package com.example.todo.dao;

import com.example.todo.model.Task;
import com.example.todo.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    // Create a new task, returns generated id (>0) or -1 on error
    public int create(Task t) {
        String sql = "INSERT INTO tasks (title, description, status) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, t.getTitle());
            stmt.setString(2, t.getDescription());
            stmt.setString(3, t.getStatus() == null ? "pending" : t.getStatus());

            int affected = stmt.executeUpdate();
            if (affected == 0) return -1;

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // Read all tasks
    public List<Task> getAll() {
        List<Task> list = new ArrayList<>();
        String sql = "SELECT id, title, description, status, created_at FROM tasks ORDER BY created_at DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Task t = new Task();
                t.setId(rs.getInt("id"));
                t.setTitle(rs.getString("title"));
                t.setDescription(rs.getString("description"));
                t.setStatus(rs.getString("status"));
                t.setCreated_at(rs.getTimestamp("created_at"));
                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Read by id
    public Task getById(int id) {
        String sql = "SELECT id, title, description, status, created_at FROM tasks WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Task t = new Task();
                    t.setId(rs.getInt("id"));
                    t.setTitle(rs.getString("title"));
                    t.setDescription(rs.getString("description"));
                    t.setStatus(rs.getString("status"));
                    t.setCreated_at(rs.getTimestamp("created_at"));
                    return t;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update title/description/status
    public boolean update(Task task) {
    String sql = "UPDATE tasks SET title = ?, description = ?, status = COALESCE(?, status) WHERE id = ?";

    try (Connection conn = DBUtil.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, task.getTitle());
        stmt.setString(2, task.getDescription());
        stmt.setString(3, task.getStatus());  // if NULL → keep old status
        stmt.setInt(4, task.getId());

        int updated = stmt.executeUpdate();
        return updated > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


    // Delete
    public boolean delete(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int deleted = stmt.executeUpdate();
            return deleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update status only
    public boolean updateStatus(int id, String status) {
        String sql = "UPDATE tasks SET status = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status == null ? "pending" : status);
            stmt.setInt(2, id);
            int updated = stmt.executeUpdate();
            return updated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
