package com.company.service;

import com.company.config.DbConnection;
import com.company.model.Item;
import com.company.model.ShoppingItem;
import com.company.model.ToDoItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

public class ToDoItemDbService implements ItemService{

    private Connection connection;

    public ToDoItemDbService() throws SQLException {
        this.connection = DbConnection.getInstance();
    }

    @Override
    public void add(Item itemToAdd) {
        String query = "insert into todoitem values (?, ?, ?, ?, ?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, itemToAdd.getId());
            preparedStatement.setString(2, itemToAdd.getContent());
            preparedStatement.setTimestamp(3, new java.sql.Timestamp(itemToAdd.getAddDate().getTime()));
            if(itemToAdd.getUpdateDate() == null) {
                preparedStatement.setNull(4, Types.TIMESTAMP);
            } else {
                preparedStatement.setTimestamp(4, new java.sql.Timestamp(itemToAdd.getUpdateDate().getTime()));
            }
            preparedStatement.setBoolean(5, ((ToDoItem) itemToAdd).isComplete());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Item getById(int id) {
        Item toDoItemToReturn = null;
        String query = "select * from todoitem where id = (?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int Id = resultSet.getInt(1);
                String content = resultSet.getString(2);
                Date addDate = new Date(resultSet.getTimestamp(3).getTime());
                Date updateDate = null;
                if(resultSet.getTimestamp(4) != null)
                    updateDate = new Date(resultSet.getTimestamp(4).getTime());
                boolean isComplete = resultSet.getBoolean(5);
                toDoItemToReturn = new ToDoItem(Id, content, addDate, updateDate, isComplete);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toDoItemToReturn;
    }

    @Override
    public List<Item> getAll() {
        List<Item> toDoItems = new ArrayList<>();
        String query = "select * from todoitem;";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String content = resultSet.getString(2);
                Date addDate = new Date(resultSet.getTimestamp(3).getTime());
                Date updateDate = null;
                if(resultSet.getTimestamp(4) != null)
                    updateDate = new Date(resultSet.getTimestamp(4).getTime());
                boolean isComplete = resultSet.getBoolean(5);
                toDoItems.add(new ToDoItem(id, content, addDate, updateDate, isComplete));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toDoItems;
    }

    @Override
    public List<Item> getItemsBetweenDates(Date d1, Date d2) {
        List<Item> toDoItemsBetweenDates = new ArrayList<>();
        String query = "select * from todoitem where addDate between (?) and (?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setTimestamp(1, new java.sql.Timestamp(d1.getTime()));
            preparedStatement.setTimestamp(2, new java.sql.Timestamp(d2.getTime()));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String content = resultSet.getString(2);
                Date addDate = new Date(resultSet.getTimestamp(3).getTime());
                Date updateDate = null;
                if(resultSet.getTimestamp(4) != null)
                    updateDate = new Date(resultSet.getTimestamp(4).getTime());
                boolean isComplete = resultSet.getBoolean(5);
                toDoItemsBetweenDates.add(new ToDoItem(id, content, addDate, updateDate, isComplete));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toDoItemsBetweenDates;
    }

    @Override
    public void delete(Item itemToDelete) {
        String query = "delete from todoitem where id = (?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, itemToDelete.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Item> getByCustomFilter(Predicate<Item> filter) {
        return Collections.emptyList();
    }

    @Override
    public void updateContent(int id, String newContent) {
        String query = "update todoitem set content = (?), updateDate = (?) where id = (?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, newContent);
            preparedStatement.setTimestamp(2, new java.sql.Timestamp(new Date().getTime()));
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCompleteStatus(int id, boolean status) {
        String query = "update todoitem set isCompleted = (?) where id = (?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setBoolean(1, status);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
