package app.validates;

import javafx.scene.control.TextField;

public class InsertBookValidate {
    public static boolean validateInput(TextField tfId, TextField tfTitle, TextField tfAuthor, TextField tfYear,
            TextField tfPages) {
        // Kiểm tra dữ liệu trống
        if (tfTitle.getText().isEmpty() || tfAuthor.getText().isEmpty() ||
                tfYear.getText().isEmpty() || tfPages.getText().isEmpty()) {
            System.out.println("Điền đầy đủ các trường.");
            return false;
        }

        // Kiểm tra kiểu số cho year và pages
        try {
            int year = Integer.parseInt(tfYear.getText());
            int pages = Integer.parseInt(tfPages.getText());

            if (year <= 0 || pages <= 0) {
                System.out.println("Năm và trang phải là số dương.");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Năm và trang phải là só integers.");
            return false;
        }

        return true;
    }
}
