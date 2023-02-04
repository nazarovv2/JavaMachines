# Chat application
### Ishga tushirish uchun zarur talablar:
1. Ma'lumotlar bazasiga ulanish uchun to'g'ri port, manzil, login va parollarni `application.properties` fayliga yozish(Ma'lumotlar bazasi sifatida PostgreSQL ishlatilgan)
2. Userga berilgan tokenlardagi sessionId va user ma'lumotlarini vaqtincha saqlab turish uchun Redis ishlatilgan. Redis server uchun to'g'ri manzil, port, login va parollarni `application.properties` fayliga kiritish
3. Kompyuter yoki serverda 8080 portining bo'sh bo'lishligi

### Dasturni ishlab chiqishda ishlatilgan texnologiya, kutubxona va o'ziga xos qulayliklar
1. Har bir DTO uchun `javax.validation` kutubxonasining birlamchi validatsiya qiladigan annotatsiyalari ishlatilgan
2. `Swagger` uchun qo'llanma yozib chiqilgan
3. Spring Security'da `JWT filter` o'rnatilgan
4. JWT ga user uchun unikal sessionID berilgan va user ma'lumotlariga qo'shib `Redis` ga saqlangan 
5. Userlar uchun role va ruxsatlar berilgan(default oddiy user sifatida kiriladi)
6. Xatoliklar bilan ishlash uchun `ExceptionHandler` ishlatilgan. Bu client tomonga xatoliklarni tushunarli bo'lib borishligini ta'minlaydi
7. `DTO` va `Entity` larda ma'lumotlarni o'girish uchun `Mapstruct` ishlatilgan
8. `DTO` lar uchun birlamchi tekshiruv uchun kerakli maydonlarga `RegEx` yozilgan
9. Xavfsizlikni ta'minlash uchun user password'i ma'lumotlar bazasiga saqlanishda `salt` qo'shib heshlab saqlangan. `Salt` avtomatik generatsiya qilinadi.
10. Rasm hajmiga qo'yilgan cheklovni tekshirish uchun `Base64 encoding` algoritmiga mos hisob-kitob qilinib, rasm hajmi Stringga qarab tekshirilgan
11. To'plamlar ustida amallar bajarishda `Stream API` lardan foydalanilgan

### Yozilishi kerak bo'lgan testlar:
1. User qo'shish.
2. Token olish.
3. Chat hosil qilish.
4. Chatga user qo'shish.
5. Chatga xabar yozish: 
   a) matnli xabar;
   b) rasmli xabar.
6. Rasmli xabarni yuklab olish