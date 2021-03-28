# habitToDoList


## 介紹
一個具備基本功能的 ToDoList，增加了習慣的功能，目標是培養習慣。以 Android 進行開發，使用 Kotlin 撰寫整個專案。以 Firebase 的 Realtime Database 來做儲存資料，為未來跨平台做準備。
##  功能

### 行事曆
    顯示待辦事項、習慣和google行事曆的活動。可新增編輯google行事曆的活動。
<img width="375" alt="截圖 2021-03-29 上午12 26 59" src="https://user-images.githubusercontent.com/40735651/112759899-a87fbc80-9027-11eb-84b9-294110577743.png">
<img width="373" alt="截圖 2021-03-29 上午12 47 01" src="https://user-images.githubusercontent.com/40735651/112760078-512e1c00-9028-11eb-8f0b-be7e6ef3bb0d.png">
<img width="370" alt="截圖 2021-03-29 上午12 46 32" src="https://user-images.githubusercontent.com/40735651/112760081-54290c80-9028-11eb-831e-25c48a44dd42.png">

###  行程表 
    輕鬆查看一天行程，讓你方便知道什麼時候要做什麼
  <img width="378" alt="截圖 2021-03-29 上午12 11 13" src="https://user-images.githubusercontent.com/40735651/112759938-cf3df300-9027-11eb-89e7-e9a7ac66a275.png">

###  培養習慣
   建立習慣，可設定時間、重要性、專案，並呈現在行程表和行事曆中，達成提醒和告知該在什麼時候完成，讓想培養的習慣成為每天必做的待辦事項。成果頁面，透過圖表和數據，增加自信心，並有每個月的完成成績，促進好勝心（成果頁面尚未做完）
 <img width="376" alt="截圖 2021-03-29 上午12 45 03" src="https://user-images.githubusercontent.com/40735651/112759991-044a4580-9028-11eb-8222-7bc62fb5dbe4.png">
###  待辦事項
   可設定時間、重要性、專案，並顯示在行程表和行事曆中，方便查看每日的待辦事項。也可以到待辦事項的區域中，查看所有待辦事項。

<center class="half">
<img width="200" alt="截圖 2021-03-29 上午12 34 08" src="https://user-images.githubusercontent.com/40735651/112759663-a2d5a700-9026-11eb-96b9-5de3f5bca1de.png"><img width="200" alt="截圖 2021-03-29 上午12 18 25" src="https://user-images.githubusercontent.com/40735651/112759674-ae28d280-9026-11eb-918c-48e304b24ee6.png"><img width="200" alt="截圖 2021-03-29 上午12 20 15" src="https://user-images.githubusercontent.com/40735651/112759677-af59ff80-9026-11eb-9713-775ccd363de8.png"><img width="200" alt="截圖 2021-03-29 上午12 17 23" src="https://user-images.githubusercontent.com/40735651/112759681-b2ed8680-9026-11eb-9d3f-879592b17c7c.png"><img width="200" alt="截圖 2021-03-29 上午12 36 12" src="https://user-images.githubusercontent.com/40735651/112759703-c993dd80-9026-11eb-8745-ade1c6a78796.png">
</center>
 



## 技術
    * kotlin
        * 使用kotlin撰寫專案
    * android custom view 
        * 用於日曆和行程表
    * firebase realtime database
        * 資料全部存於firebase realtime database之中
    * Android Calendar provider
        * 用於串連google行事曆
    * View page
        * 用於首頁的時間顯示和日曆
    * RecyclerView
        * 用於所有清單、行程表和新增待辦事項中月、年的時間增加
