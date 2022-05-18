package com.company.service;

import com.company.config.DbConnection;
import com.company.model.Item;
import com.company.model.ShoppingItem;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.function.Predicate;

public class ShoppingItemDbService implements ItemService{

    private Connection connection;

    public ShoppingItemDbService() throws SQLException {
        this.connection = DbConnection.getInstance();
    }

    @Override
    public void add(Item itemToAdd) {
        String query = "insert into shoppingitem values (?, ?, ?, ?, ?, ?, null)";
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
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Item getById(int id) {
        Item shopItemToReturn = null;
        String query = "select * from shoppingitem where id = (?)";
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
                int quantity = resultSet.getInt(5);
                double price = resultSet.getDouble(6);
                Optional<Integer> listId = Optional.ofNullable(resultSet.getInt(7));
                if(resultSet.wasNull()) {
                    listId = Optional.ofNullable(null);
                }
                shopItemToReturn = new ShoppingItem(Id, content, addDate, updateDate, quantity, price, listId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shopItemToReturn;
    }

    @Override
    public List<Item> getAll() {
        List<Item> shoppingItems = new ArrayList<>();
        String query = "select * from shoppingitem;";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String content = resultSet.getString(2);
                Date addDate = new Date(resultSet.getTimestamp(3).getTime());
                Date updateDate = null;
                if(resultSet.getTimestamp(4) != null)
                    updateDate = new Date(resultSet.getTimestamp(4).getTime());
                int quantity = resultSet.getInt(5);
                double price = resultSet.getDouble(6);
                Optional<Integer> listId = Optional.ofNullable(resultSet.getInt(7));
                if(resultSet.wasNull()) {
                    listId = Optional.ofNullable(null);
                }
                shoppingItems.add(new ShoppingItem(id, content, addDate, updateDate, quantity, price, listId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shoppingItems;
    }

    @Override
    public List<Item> getItemsBetweenDates(Date d1, Date d2) {
        List<Item> shoppingItemsBetweenDates = new ArrayList<>();
        String query = "select * from shoppingitem where addDate between (?) and (?)";
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
                int quantity = resultSet.getInt(5);
                double price = resultSet.getDouble(6);
                Optional<Integer> listId = Optional.ofNullable(resultSet.getInt(7));
                if(resultSet.wasNull()) {
                    listId = Optional.ofNullable(null);
                }
                shoppingItemsBetweenDates.add(new ShoppingItem(id, content, addDate, updateDate, quantity, price, listId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shoppingItemsBetweenDates;
    }

    @Override
    public void delete(Item itemToDelete) {
        String query = "delete from shoppingitem where id = (?)";
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
        String query = "update shoppingitem set content = (?), updateDate = (?) where id = (?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, newContent);
            preparedStatement.setTimestamp(2, new java.sql.Timestamp(new Date().getTime()));
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePrice(int id, double newPrice) {
        String query = "update shoppingitem set price = (?) where id = (?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, newPrice);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateQuantity(int id, int newQuantity) {
        String query = "update shoppingitem set quantity = (?) where id = (?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, newQuantity);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
