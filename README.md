# 🧩 Bir Kelime Bir İşlem

**Bir Kelime Bir İşlem**, klasik Türk televizyon programından ilham alan; kelime bilgisi ve matematiksel düşünme yeteneğini aynı potada eriten modern bir **Java Swing** masaüstü oyunudur.  
Hızlı, akıcı ve karanlık temalı arayüzüyle hem eğlenceli hem de zihni zorlayıcı bir deneyim sunar.

---

## 🎮 Oyun Modları

### 🔤 Kelime Oyunu
Amaç: Verilen **8 harfi** kullanarak **en uzun ve geçerli kelimeyi** oluşturmak.

**Nasıl Oynanır?**
1. **Harf Seçimi:** Harf havuzundaki bir harfe **sağ tık** yaparak kelime alanına ekleyin.
2. **Geri Alma:** Yanlış bir seçim yaptıysanız, kelime alanındaki harfe **sol tık** yaparak geri gönderebilirsiniz.
3. **Joker:** Takılırsanız sol taraftaki **Joker Paneli**nden ekstra harf alabilirsiniz.
4. **Gönderim:** Süre dolduğunda veya manuel olarak gönderdiğinizde kelimeniz otomatik kontrol edilir.

📌 Kelime bulunamazsa oyun **geçerli bir çözümü** gösterir.

---

### 🔢 Sayı Oyunu
Amaç: Verilen **5 küçük sayı + 1 büyük sayı** ile hedef sayıya ulaşmak.

**Nasıl Oynanır?**
1. Sayıları alt kısımdaki işlem alanlarına **sürükle & bırak** yöntemiyle yerleştirin.
2. Ortadaki operatör butonundan işlemi seçin: **+ − × ÷**
3. **=** tuşuna basarak sonucu hesaplayın.
4. Ortaya çıkan yeni sayılar sağ tarafta listelenir ve tekrar kullanılabilir.

🎯 Stratejik hamlelerle hedef sayıya adım adım yaklaşın!

---

## 📸 Oyun İçi Görseller

### Ana Menü
<img width="100%" alt="Main Menu" src="https://github.com/user-attachments/assets/654e5b88-b0f8-4125-bbeb-8e510d39f2ae" />

### Kelime Oyunu
<img width="100%" alt="Word Game" src="https://github.com/user-attachments/assets/51bbc2f8-1523-414e-8d46-fb22b71eccc1" />

### Sayı Oyunu
<img width="100%" alt="Number Game" src="https://github.com/user-attachments/assets/ce5b7925-7c48-4aa6-8a17-f5e3965955cd" />

---

## 🧠 Teknik Mimari
Proje, **UI** ve **iş mantığını** net bir şekilde ayıran modüler bir yapıya sahiptir.

| Sınıf | Açıklama |
|------|---------|
| **GameManager.java** | Uygulamanın merkezi. Menü geçişleri ve oyun döngüsünü yönetir. |
| **Logic.java** | Kelime doğrulama ve matematiksel hedef üretme algoritmaları. |
| **DataBase.java** | Skorların dosyaya kaydı ve leaderboard yönetimi. |
| **Frame.java** | Ana pencere ve özel Swing çizimleri. |
| **Word.java / Number.java** | Oyun modlarına özel UI panelleri. |
| **Custom Components** | Dark Theme için özel buton ve label sınıfları. |

---

## ✨ Özellikler
- 🎨 **Modern Dark Theme** – Özel tasarlanmış Swing bileşenleri
- ⏱️ **Zorluk Seviyeleri** – Easy / Normal / Hard
- 🥇 **Kalıcı Leaderboard** – Skorlar yerel olarak saklanır
- 👤 **Kullanıcı Profilleri** – Her oyuncu kendi ilerlemesini takip edebilir
- ⚡ **Akıcı Performans** – Hafif ve hızlı masaüstü deneyimi

---

## 🚀 Kurulum & Çalıştırma

### Gereksinimler
- **JDK 8 veya üzeri**

### Kurulum
```bash
git clone https://github.com/yusuflevent12/bir_kelime_bir_islem.git
```

### Çalıştırma
```bash
cd bir_kelime_bir_islem/src
javac GameManager.java
java GameManager
```

---

## 👨‍💻 Geliştirici

Developed with ❤️ by **yusuflevent12**

> Geri bildirimler, katkılar ve yıldızlar ⭐ her zaman motive eder!
