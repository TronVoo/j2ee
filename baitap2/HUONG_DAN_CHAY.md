# Hướng Dẫn Chạy Ứng Dụng và Test trên Postman

## 📋 Yêu Cầu Hệ Thống

- Java 17 hoặc cao hơn
- Maven 3.6+ (hoặc sử dụng Maven Wrapper có sẵn trong dự án)
- Postman (để test API)

## 🚀 Cách Chạy Ứng Dụng

### Cách 1: Sử dụng Maven Wrapper (Khuyến nghị)

**Trên Windows:**
```bash
.\mvnw.cmd spring-boot:run
```

**Trên Linux/Mac:**
```bash
./mvnw spring-boot:run
```

### Cách 2: Sử dụng Maven (nếu đã cài đặt)

```bash
mvn spring-boot:run
```

### Cách 3: Build và chạy JAR file

```bash
# Build project
mvn clean package

# Chạy JAR file
java -jar target/bai2-0.0.1-SNAPSHOT.jar
```

## ✅ Kiểm Tra Ứng Dụng Đã Chạy

Sau khi chạy, bạn sẽ thấy log tương tự:
```
Started Bai2Application in X.XXX seconds
```

Ứng dụng sẽ chạy tại: **http://localhost:8080**

## 📡 API Endpoints

Base URL: `http://localhost:8080/api/books`

### 1. GET - Lấy danh sách tất cả sách
- **Method:** `GET`
- **URL:** `http://localhost:8080/api/books`
- **Headers:** Không cần
- **Body:** Không cần

### 2. GET - Lấy sách theo ID
- **Method:** `GET`
- **URL:** `http://localhost:8080/api/books/{id}`
- **Ví dụ:** `http://localhost:8080/api/books/1`
- **Headers:** Không cần
- **Body:** Không cần

### 3. POST - Thêm sách mới
- **Method:** `POST`
- **URL:** `http://localhost:8080/api/books`
- **Headers:** 
  - `Content-Type: application/json`
- **Body (raw JSON):**
```json
{
  "id": 1,
  "title": "Java Programming",
  "author": "John Doe"
}
```

### 4. PUT - Cập nhật thông tin sách
- **Method:** `PUT`
- **URL:** `http://localhost:8080/api/books/{id}`
- **Ví dụ:** `http://localhost:8080/api/books/1`
- **Headers:** 
  - `Content-Type: application/json`
- **Body (raw JSON):**
```json
{
  "title": "Java Programming Updated",
  "author": "Jane Smith"
}
```

### 5. DELETE - Xóa sách theo ID
- **Method:** `DELETE`
- **URL:** `http://localhost:8080/api/books/{id}`
- **Ví dụ:** `http://localhost:8080/api/books/1`
- **Headers:** Không cần
- **Body:** Không cần

## 🧪 Hướng Dẫn Test trên Postman

### Bước 1: Mở Postman
Mở ứng dụng Postman trên máy tính của bạn.

### Bước 2: Tạo Request Collection (Tùy chọn)
1. Click **New** → **Collection**
2. Đặt tên: "Book Management API"
3. Click **Create**

### Bước 3: Test từng API

#### Test 1: Thêm sách mới (POST)
1. Click **New** → **HTTP Request**
2. Chọn method: **POST**
3. Nhập URL: `http://localhost:8080/api/books`
4. Vào tab **Headers**, thêm:
   - Key: `Content-Type`
   - Value: `application/json`
5. Vào tab **Body**:
   - Chọn **raw**
   - Chọn **JSON** từ dropdown
   - Nhập JSON:
   ```json
   {
     "id": 1,
     "title": "Java Programming",
     "author": "John Doe"
   }
   ```
6. Click **Send**
7. Kết quả mong đợi: `"Book added successfully!"`

#### Test 2: Lấy tất cả sách (GET)
1. Tạo request mới
2. Chọn method: **GET**
3. Nhập URL: `http://localhost:8080/api/books`
4. Click **Send**
5. Kết quả mong đợi: Mảng JSON chứa các sách đã thêm

#### Test 3: Lấy sách theo ID (GET)
1. Tạo request mới
2. Chọn method: **GET**
3. Nhập URL: `http://localhost:8080/api/books/1`
4. Click **Send**
5. Kết quả mong đợi: JSON object của sách có id = 1

#### Test 4: Cập nhật sách (PUT)
1. Tạo request mới
2. Chọn method: **PUT**
3. Nhập URL: `http://localhost:8080/api/books/1`
4. Vào tab **Headers**, thêm:
   - Key: `Content-Type`
   - Value: `application/json`
5. Vào tab **Body**:
   - Chọn **raw**
   - Chọn **JSON**
   - Nhập JSON:
   ```json
   {
     "title": "Java Programming Updated",
     "author": "Jane Smith"
   }
   ```
6. Click **Send**
7. Kết quả mong đợi: `"Book updated successfully!"`

#### Test 5: Xóa sách (DELETE)
1. Tạo request mới
2. Chọn method: **DELETE**
3. Nhập URL: `http://localhost:8080/api/books/1`
4. Click **Send**
5. Kết quả mong đợi: `"Book deleted successfully!"`

## 📝 Lưu Ý Quan Trọng

1. **Thứ tự test:** Nên test theo thứ tự:
   - POST (thêm sách) → GET all → GET by ID → PUT → DELETE

2. **Dữ liệu mẫu:** Bạn có thể thêm nhiều sách với các ID khác nhau:
   ```json
   {"id": 1, "title": "Book 1", "author": "Author 1"}
   {"id": 2, "title": "Book 2", "author": "Author 2"}
   {"id": 3, "title": "Book 3", "author": "Author 3"}
   ```

3. **Lỗi thường gặp:**
   - **404 Not Found:** Kiểm tra URL và đảm bảo ứng dụng đang chạy
   - **415 Unsupported Media Type:** Đảm bảo đã set header `Content-Type: application/json`
   - **400 Bad Request:** Kiểm tra format JSON trong body

4. **Dữ liệu lưu trong memory:** Dữ liệu chỉ tồn tại khi ứng dụng đang chạy. Khi restart, dữ liệu sẽ mất.

## 🎯 Ví Dụ Test Hoàn Chỉnh

### Scenario: Quản lý sách hoàn chỉnh

1. **Thêm sách 1:**
   - POST `http://localhost:8080/api/books`
   - Body: `{"id": 1, "title": "Spring Boot Guide", "author": "Spring Team"}`

2. **Thêm sách 2:**
   - POST `http://localhost:8080/api/books`
   - Body: `{"id": 2, "title": "Java Fundamentals", "author": "Oracle"}`

3. **Xem tất cả sách:**
   - GET `http://localhost:8080/api/books`
   - Kết quả: Mảng 2 sách

4. **Xem sách ID 1:**
   - GET `http://localhost:8080/api/books/1`
   - Kết quả: Thông tin sách ID 1

5. **Cập nhật sách ID 1:**
   - PUT `http://localhost:8080/api/books/1`
   - Body: `{"title": "Spring Boot Guide - Updated", "author": "Spring Team"}`

6. **Xóa sách ID 2:**
   - DELETE `http://localhost:8080/api/books/2`

7. **Xem lại tất cả sách:**
   - GET `http://localhost:8080/api/books`
   - Kết quả: Chỉ còn 1 sách (ID 1)

## 🛠️ Troubleshooting

### Ứng dụng không chạy được
- Kiểm tra Java version: `java -version` (cần Java 17+)
- Kiểm tra port 8080 có bị chiếm không
- Xem log lỗi trong console

### Postman không kết nối được
- Đảm bảo ứng dụng đang chạy
- Kiểm tra URL có đúng không
- Thử truy cập `http://localhost:8080/api/books` trên trình duyệt

---

**Chúc bạn test thành công! 🎉**

