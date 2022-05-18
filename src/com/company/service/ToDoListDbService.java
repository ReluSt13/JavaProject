package com.company.service;

import com.company.config.DbConnection;
import com.company.model.*;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.function.Predicate;

public class ToDoListDbService implements ListService{

    private Connection connection;

    public ToDoListDbService() throws SQLException {
        this.connection = DbConnection.getInstance();
    }

    @Override
    public void add(Catalogue listToAdd) {
        String query = "insert into todolist values (?, ?, ?, 0)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, listToAdd.getId());
            preparedStatement.setString(2, listToAdd.getListName());
            preparedStatement.setTimestamp(3, new java.sql.Timestamp(listToAdd.getAddDate().getTime()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Catalogue getById(int id) {
        Catalogue catalogueToReturn = null;
        String query = "select * from todolist where id = (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int Id = resultSet.getInt(1);
                String listName = resultSet.getString(2);
                Date addDate = new Date(resultSet.getTimestamp(3).getTime());
                Double percentageComplete = resultSet.getDouble(4);
                catalogueToReturn = new ToDoList(Id, listName, addDate, percentageComplete);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return catalogueToReturn;
    }

    @Override
    public List<Catalogue> getAll() {
        List<Catalogue> cataloguesToReturn = new ArrayList<>();
        String query = "select * from todolist";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int Id = resultSet.getInt(1);
                String listName = resultSet.getString(2);
                Date addDate = new Date(resultSet.getTimestamp(3).getTime());
                Double percentageComplete = resultSet.getDouble(4);
                cataloguesToReturn.add(new ToDoList(Id, listName, addDate, percentageComplete));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cataloguesToReturn;
    }

    @Override
    public List<Catalogue> getListsBetweenDates(Date d1, Date d2) {
        return Collections.emptyList();
    }

    @Override
    public void delete(Catalogue ListToDelete) {
        String query = "delete from todolist where id = (?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, ListToDelete.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Catalogue> getByCustomFilter(Predicate<Catalogue> filter) {
        return Collections.emptyList();
    }

    @Override
    public void addItem(int id, Item itemToAdd) {
        String query = "insert into todoitem values (?, ?, ?, ?, ?, ?)";
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
            preparedStatement.setInt(6, getById(id).getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        updatePercentageComplete(id);
    }

    private void updatePercentageComplete(int id) {
        List<Item> getItems = getAllWithNListId(id);
        int nrOfCompletedItems = 0;
        for (Item item :
                getItems) {
            if (((ToDoItem) item).isComplete())
                nrOfCompletedItems++;
        }
        double PercentageComplete = (double) nrOfCompletedItems / getItems.size();
        String query = "update todolist set percentageComplete = (?) where id = (?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, PercentageComplete);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Item> getAllWithNListId(int N) {
        String getAllItemsWithNListId = "select * from todoitem where listId = (?)";
        List<Item> toDoItemsWithNListId = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement(getAllItemsWithNListId)) {
            preparedStatement.setInt(1, N);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int Id = resultSet.getInt(1);
                String content = resultSet.getString(2);
                Date addDate = new Date(resultSet.getTimestamp(3).getTime());
                Date updateDate = null;
                if(resultSet.getTimestamp(4) != null)
                    updateDate = new Date(resultSet.getTimestamp(4).getTime());
                boolean isComplete = resultSet.getBoolean(5);
                Optional<Integer> listId = Optional.ofNullable(resultSet.getInt(6));
                toDoItemsWithNListId.add(new ToDoItem(Id, content, addDate, updateDate, isComplete, listId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toDoItemsWithNListId;
    }

    @Override
    public void updateName(int id, String newName) {
        String query = "update todolist set listName = (?) where id = (?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
