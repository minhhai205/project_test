package app.controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import app.config.DatabaseConnection;
import app.domain.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import app.validates.InsertBookValidate;

public class MainController implements Initializable {
    @FXML
    private TextField tfId, tfTitle, tfAuthor, tfYear, tfPages;

    @FXML
    private Button btnInsert, btnDelete, btnUpdate;

    @FXML
    private TableView<Book> tvBooks;

    @FXML
    private TableColumn<Book, Integer> colId;

    @FXML
    private TableColumn<Book, String> colTitle;

    @FXML
    private TableColumn<Book, String> colAuthor;

    @FXML
    private TableColumn<Book, Integer> colYear;

    @FXML
    private TableColumn<Book, Integer> colPages;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showBooks();
    }

    /**
     * Click button event
     */
    @FXML
    private void handdleButtonAction(ActionEvent e) {
        if (e.getSource() == btnInsert) {
            insertRecord();
        } else if (e.getSource() == btnUpdate) {
            updateRecord();
        } else if (e.getSource() == btnDelete) {
            deleteRecord();
        }
    }

    /**
     * Lấy danh sách sách từ database và trả về dưới dạng ObservableList
     * 
     * @param connection Kết nối đến cơ sở dữ liệu để thực hiện truy vấn.
     * @param ps         PreparedStatement để thực thi lệnh với cơ sở dữ liệu.
     * @param rs         ResultSet để nhận dữ liệu trả về từ cơ sở dữ liệu
     */
    public ObservableList<Book> getBookList() {
        ObservableList<Book> bookList = FXCollections.observableArrayList();
        Connection connection = null;
        String query = "SELECT * FROM books";
        Statement st;
        ResultSet rs;

        try {
            connection = DatabaseConnection.getInstance().getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(query);
            Book book;
            while (rs.next()) {
                book = new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("year"),
                        rs.getInt("pages"));

                bookList.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookList;
    }

    public void showBooks() {
        ObservableList<Book> list = getBookList();

        colId.setCellValueFactory(new PropertyValueFactory<Book, Integer>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        colYear.setCellValueFactory(new PropertyValueFactory<Book, Integer>("year"));
        colPages.setCellValueFactory(new PropertyValueFactory<Book, Integer>("pages"));

        tvBooks.setItems(list);
    }

    /**
     * Thêm bản ghi vào database và cập nhật table
     */
    private void insertRecord() {
        if (!InsertBookValidate.validateInput(tfId, tfTitle, tfAuthor, tfYear, tfPages)) {
            return;
        }
        String query = "INSERT INTO books (id, title, author, year, pages) VALUES (?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = DatabaseConnection.getInstance().getConnection();
            ps = connection.prepareStatement(query);

            ps.setInt(1, Integer.parseInt(tfId.getText()));
            ps.setString(2, tfTitle.getText());
            ps.setString(3, tfAuthor.getText());
            ps.setInt(4, Integer.parseInt(tfYear.getText()));
            ps.setInt(5, Integer.parseInt(tfPages.getText()));

            ps.executeUpdate();

            showBooks(); // Reset bảng sau mỗi lần thêm bản ghi mới
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, ps, null, null);
        }
    }

    /**
     * Update bản ghi vào database và cập nhật table
     */
    private void updateRecord() {
        if (!InsertBookValidate.validateInput(tfId, tfTitle, tfAuthor, tfYear, tfPages)) {
            return;
        }

        String query = "UPDATE books SET title = ?, author = ?, year = ?, pages = ? WHERE id = ?";

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement ps = connection.prepareStatement(query);

            // Set các tham số cho PreparedStatement
            ps.setString(1, tfTitle.getText());
            ps.setString(2, tfAuthor.getText());
            ps.setInt(3, Integer.parseInt(tfYear.getText()));
            ps.setInt(4, Integer.parseInt(tfPages.getText()));
            ps.setInt(5, Integer.parseInt(tfId.getText()));

            // Thực thi lệnh update
            ps.executeUpdate();

            // Hiển thị lại danh sách sách sau khi cập nhật
            showBooks();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteRecord() {
        // Kiểm tra xem ô nhập ID có trống không
        if (tfId.getText() == null || tfId.getText().isEmpty()) {
            return;
        }

        String query = "DELETE FROM books WHERE id = ?";

        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = DatabaseConnection.getInstance().getConnection();
            ps = connection.prepareStatement(query);

            // set giá trị tham số id trong câu truy vấn
            ps.setInt(1, Integer.parseInt(tfId.getText()));

            // Thực hiện xóa bản ghi
            ps.executeUpdate();

            showBooks();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, ps, null, null);
        }
    }

    /**
     * Đóng các tài nguyên kết nối
     */
    private void closeResources(Connection connection, PreparedStatement ps, Statement st, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (st != null) {
                st.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
