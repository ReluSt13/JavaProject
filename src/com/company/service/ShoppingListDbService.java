package com.company.service;

import com.company.config.DbConnection;
import com.company.model.*;

import java.security.interfaces.RSAKey;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.function.Predicate;

public class ShoppingListDbService implements ListService{

    private Connection connection;

    public ShoppingListDbService() throws SQLException {
        this.connection = DbConnection.getInstance();
    }

    @Override
    public void add(Catalogue listToAdd) {
        String query = "insert into shoppinglist values (?, ?, ?, ?, 0)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, listToAdd.getId());
            preparedStatement.setString(2, listToAdd.getListName());
            preparedStatement.setTimestamp(3, new java.sql.Timestamp(listToAdd.getAddDate().getTime()));
            preparedStatement.setDouble(4, ((ShoppingList) listToAdd).getMaxPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Catalogue getById(int id) {
        Catalogue catalogueToReturn = null;
        String query = "select * from shoppinglist where id = (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int Id = resultSet.getInt(1);
                String listName = resultSet.getString(2);
                Date addDate = new Date(resultSet.getTimestamp(3).getTime());
                double maxPrice = resultSet.getDouble(4);
                double actualPrice = resultSet.getDouble(5);
                catalogueToReturn = new ShoppingList(Id, listName, addDate, maxPrice, actualPrice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return catalogueToReturn;
    }

    @Override
    public List<Catalogue> getAll() {
        List<Catalogue> cataloguesToReturn = new ArrayList<>();
        String query = "select * from shoppinglist";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int Id = resultSet.getInt(1);
                String listName = resultSet.getString(2);
                Date addDate = new Date(resultSet.getTimestamp(3).getTime());
                double maxPrice = resultSet.getDouble(4);
                double actualPrice = resultSet.getDouble(5);
                cataloguesToReturn.add(new ShoppingList(Id, listName, addDate, maxPrice, actualPrice));
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
        String query = "delete from shoppinglist where id = (?)";
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
        String query = "insert into shoppingitem values (?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, itemToAdd.getId());
            preparedStatement.setString(2, itemToAdd.getContent());
            preparedStatement.setTimestamp(3, new java.sql.Timestamp(itemToAdd.getAddDate().getTime()));
            if(itemToAdd.getUpdateDate() == null) {
                preparedStatement.setNull(4, Types.TIMESTAMP);
            } else {
                preparedStatement.setTimestamp(4, new java.sql.Timestamp(itemToAdd.getUpdateDate().getTime()));
            }
            preparedStatement.setInt(5, ((ShoppingItem) itemToAdd).getQuantity());
            preparedStatement.setDouble(6, ((ShoppingItem) itemToAdd).getPrice());
            preparedStatement.setInt(7, getById(id).getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        updateActualPrice(id);
    }

    private List<Item> getAllWithNListId(int N) {
        String getAllItemsWithNListId = "select * from shoppingitem where listId = (?)";
        List<Item> shoppingItemsWithNListId = new ArrayList<>();
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
                int quantity = resultSet.getInt(5);
                double price = resultSet.getDouble(6);
                Optional<Integer> listId = Optional.ofNullable(resultSet.getInt(7));
                shoppingItemsWithNListId.add(new ShoppingItem(Id, content, addDate, updateDate, quantity, price, listId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shoppingItemsWithNListId;
    }

    private void updateActualPrice(int id) {
        List<Item> getItems = getAllWithNListId(id);
        double ActualPrice = 0;
        for (Item item :
                getItems) {
            ActualPrice += ((ShoppingItem) item).getTotalPrice();
        }
        String query = "update shoppinglist set actualPrice = (?) where id = (?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, ActualPrice);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateName(int id, String newName) {
        String query = "update shoppinglist set listName = (?) where id = (?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
     }

     public void assignItemToList(int listId, int itemId) {
        String query = "update shoppingitem set listId = (?) where id = (?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, listId);
            preparedStatement.setInt(2, itemId);
            preparedStatement.executeUpdate();
            updateActualPrice(listId);
        } catch (SQLException e){
            e.printStackTrace();
        }
     }

     public void updateMaxPrice(int listId, double newMaxPrice) {
         String query = "update shoppinglist set maxPrice = (?) where id = (?)";
         try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
             preparedStatement.setDouble(1, newMaxPrice);
             preparedStatement.setInt(2, listId);
             preparedStatement.executeUpdate();
         } catch (SQLException e){
             e.printStackTrace();
         }
     }

     public void updatePriceOfItem(int listId, int itemId, double newPrice) {
         String query = "update shoppingitem set price = (?) where id = (?) and listId = (?)";
         try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
             preparedStatement.setDouble(1, newPrice);
             preparedStatement.setInt(2, itemId);
             preparedStatement.setInt(3, listId);
             preparedStatement.executeUpdate();
             updateActualPrice(listId);
         } catch (SQLException e){
             e.printStackTrace();
         }
     }

     public void updateQuantityOfItem(int listId, int itemId, int newQuantity) {
         String query = "update shoppingitem set quantity = (?) where id = (?) and listId = (?)";
         try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
             preparedStatement.setInt(1, newQuantity);
             preparedStatement.setInt(2, itemId);
             preparedStatement.setInt(3, listId);
             preparedStatement.executeUpdate();
             updateActualPrice(listId);
         } catch (SQLException e){
             e.printStackTrace();
         }
     }
}
