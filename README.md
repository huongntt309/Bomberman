# Bomberman Project
## Project Database INT2211-40
## GV Lý thuyết: cô Nguyễn Thị Hậu
## GV Thực hành: cô Nguyễn Thị Cẩm Vân


### Sinh Viên
- Nguyễn Thị Thuý Hường
    - 21020467 HugATP

### Mô tả
- Game sử dụng:
    - UI : javafx + scenebuilder
    - database: sqlite

### GamePlay
- Bomber:
    - Di chuyển theo hướng mũi tên
    - Space : Đặt bomb
- Enemy:
    - Easy Mode : đi theo hướng được mặc định
    - Medium Mode : đi theo bomber và không biết né bomb
    - Hard Mode : đi theo bomber và biết né bomb
- Item:
    - Speed : x2 speed
    - Bomb : +1 số bomb của bomber
    - Bomb Size : +1 bán kính bomb nổ
    - Point Up : +2000 pts
    - Key : chìa khóa
    - Door : đích để sang map mới
- Score:
  - Collect Item :  + 300 pts
  - Kill Enemy :    + 500 pts
  - Break Brick :   + 100 pts
  - Up Level Game : + 500 pts

### Các chức năng chính
- Login / Logout / Sign Up:
    - User có thể tự đăng ký account
    - Account sẽ lưu trữ lại thông tin cá nhân, các tùy chọn (nhạc), score của user

- Setting Music:
    - Setting : tùy chỉnh background music và âm lượng (lưu lại data, lần sau login sẽ lấy data từ trước đó (song, volume))

- History :
    - xem lại lịch sử chơi từ trước đến nay (score, time, character)
    - highest score
    - most favourite character

- Ranking:
    - Sau mỗi ván đấu, người chơi có thể xem được
        - Ranking top 3 toàn bộ
        - Ranking top 3 trong ngày

- Admin Mode:
    - account admin:
        - username : admin
        - password : 321
    - Admin có khả năng xem được tất cả account của user đã đăng ký, tìm kiếm và xóa useraccount
    - Admin xem được lịch sử chơi từng user trong mỗi lần chơi (history)

## DATABASE
- sử dụng sqlite
- truy vấn sql trong các file "src/main/java/bomberman/Model"
- tương tác với user trong các file "src/main/java/bomberman/Controller"
### EER Diagram
  <img src="EER_Diagram.png">

update tới ngày 22/10/2022

