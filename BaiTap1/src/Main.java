import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Book> listBook = new ArrayList<>();
        try (Scanner x = new Scanner(System.in)) {
            String msg = """
                    Chương trình quản lý sách
                    1. Thêm 1 cuốn sách
                    2. Xóa 1 cuốn sách
                    3. Thay đổi sách
                    4. Xuất thông tin
                    5. Tìm sách lập trình
                    6. Lấy sách tối đa theo giá
                    7. Tìm kiếm theo tác giả
                    0. Thoát
                    Chọn chức năng: """;

            int chon = 0;
            do {
                System.out.print(msg);
                chon = x.nextInt();
                x.nextLine();

                switch (chon) {
                    case 1 -> {
                        Book newBook = new Book();
                        newBook.input();
                        listBook.add(newBook);
                    }
                    case 2 -> {
                        System.out.print("Nhập vào mã sách cần xóa: ");
                        int bookid = x.nextInt();
                        Book find = listBook.stream()
                                .filter(p -> p.getId() == bookid)
                                .findFirst()
                                .orElse(null);
                        if (find != null) {
                            listBook.remove(find);
                            System.out.println("Đã xóa sách thành công");
                        } else {
                            System.out.println("Không tìm thấy sách");
                        }
                    }
                    case 3 -> {
                        System.out.print("Nhập vào mã sách cần điều chỉnh: ");
                        int bookid = x.nextInt();
                        x.nextLine();
                        Book find = listBook.stream()
                                .filter(p -> p.getId() == bookid)
                                .findFirst()
                                .orElse(null);
                        if (find != null) {
                            System.out.print("Nhập tên mới: ");
                            find.setTitle(x.nextLine());
                            System.out.print("Nhập tác giả mới: ");
                            find.setAuthor(x.nextLine());
                            System.out.print("Nhập giá mới: ");
                            find.setPrice(x.nextLong());
                            System.out.println("Đã cập nhật sách thành công");
                        } else {
                            System.out.println("Không tìm thấy sách");
                        }
                    }
                    case 4 -> {
                        System.out.println("\nXuất thông tin danh sách");
                        if (listBook.isEmpty()) {
                            System.out.println("Danh sách trống");
                        } else {
                            listBook.forEach(p -> p.output());
                        }
                    }
                    case 5 -> {
                        List<Book> list5 = listBook.stream()
                                .filter(u -> u.getTitle().toLowerCase().contains("lập trình"))
                                .toList();
                        if (list5.isEmpty()) {
                            System.out.println("Không tìm thấy sách lập trình");
                        } else {
                            list5.forEach(Book::output);
                        }
                    }
                    case 6 -> {
                        if (listBook.isEmpty()) {
                            System.out.println("Danh sách trống");
                        } else {
                            Book maxBook = listBook.stream()
                                    .max((a, b) -> Long.compare(a.getPrice(), b.getPrice()))
                                    .orElse(null);
                            System.out.println("Sách có giá cao nhất:");
                            maxBook.output();
                        }
                    }
                    case 7 -> {
                        System.out.print("Nhập tên tác giả cần tìm: ");
                        String author = x.nextLine();
                        List<Book> list7 = listBook.stream()
                                .filter(u -> u.getAuthor().toLowerCase().contains(author.toLowerCase()))
                                .toList();
                        if (list7.isEmpty()) {
                            System.out.println("Không tìm thấy sách của tác giả này");
                        } else {
                            list7.forEach(Book::output);
                        }
                    }
                    case 0 -> System.out.println("Thoát chương trình");
                    default -> System.out.println("Chức năng không tồn tại");
                }
            } while (chon != 0);
        }
    }
}