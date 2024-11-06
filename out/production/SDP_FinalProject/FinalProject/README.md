<h1 align="center">Welcome to RestaurantAppSwing!<img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTlNBZiQjsuJNvxKM8vgi3EEh7iuuLwLB6efg&s" height="32"/></h1>
Welcome to **RestaurantAppSwing**, a delightful online restaurant ordering app built with 💖 in Java Swing! 🍴✨

This application lets you explore a delicious menu, add your favorite dishes to your order, and track your meal in a super user-friendly way. Whether you're craving something sweet 🍰 or a savory main course 🍝, we’ve got you covered! 😋

---

## Features 🌟

- **Filter by Categories**: Browse through our menu with easy category filters — whether it’s Main Courses, Drinks, Snacks, or Desserts. 🍽
- **Add and Remove Dishes**: Choose what you love and remove the items you change your mind about. ❤️
- **Track Your Orders**: Want to see what you’ve ordered? Your order history is always here to remind you of your yummy past meals. 🍔🥤
- **Order Finalization**: Check your total with a small service charge and confirm your order in just a few clicks! 💸✨
- **Stylish and Interactive UI**: With a beautiful and easy-to-navigate interface, placing orders has never been so fun! 🎨

---

## Design Patterns Used in the App 🎨

### 1. Factory Pattern (Creational) 🍴
The Factory Pattern is used to create different types of dishes (e.g., Main Course, Drinks, Desserts, and Snacks). We have separate factories for each category:
- `MainDishFactory`
- `DrinkFactory`
- `SnackFactory`
- `DessertFactory`

Each factory is responsible for creating its respective dishes, ensuring that the correct category of food is added to the menu with a consistent interface. 🍕🍹

### 2. Singleton Pattern (Creational) 🌐
The Singleton Pattern is used for managing the Order class. The order is a shared resource throughout the application, and we need only one instance of it. Using a singleton ensures that we don't accidentally create multiple orders, maintaining the integrity of the order process. 🍔

### 3. Observer Pattern (Behavioral) 👀
The Observer Pattern is employed to keep the user interface in sync with the current order. Whenever an item is added or removed, the order view (displayed in a `JTextArea`) is updated automatically. This provides a smooth experience where users can immediately see changes in their order. 📝

### 4. Model-View-Controller (MVC) Pattern 🎮
The MVC Pattern is the backbone of the app’s structure, helping us organize the code in a clean, maintainable way:
- **Model**: Represents the data and logic of the application, such as `Order`, `Dish`, and `DishCategory`.
- **View**: The user interface, created using Java Swing, where users interact with the app, select dishes, and see their orders.
- **Controller**: Handles user input (e.g., selecting a dish, adding it to the order, and finalizing the order), updating the model and view accordingly.

### 5. Decorator Pattern (Structural) ✨
The Decorator Pattern allows us to dynamically add functionality to objects without changing their structure. In the app, we use this pattern to decorate our order items with extra features like special requests (e.g., extra cheese, no salt) or extra toppings.

For example, a base dish like a Burger can be decorated with a `CheeseDecorator` to add extra cheese or a `SpicyDecorator` to make it spicy! 🌶🍔

### 6. Command Pattern (Behavioral) 🎯
The Command Pattern is used to encapsulate requests as objects. Each action, such as adding an item to the order, removing an item, or finalizing the order, is treated as a command:
- `AddDishCommand`: Adds a selected dish to the order.
- `RemoveDishCommand`: Removes a dish from the order.
- `FinalizeOrderCommand`: Finalizes and processes the order.

This pattern decouples actions from the user interface, making the application easier to maintain and extend. It also allows us to implement features like undo functionality in the future. 🔄

---

## How to Use 🧑‍🍳

1. **Start the App**: Click on the "Start" button, and the restaurant menu will pop up. 🍽
2. **Choose a Category**: Filter your menu items by clicking on the category dropdown. 🥘🥤
3. **Pick Your Dish**: Select a dish from the dish menu and add it to your order. You can easily remove or clear dishes anytime! 🍕🍰
4. **Finalize the Order**: When you're ready to order, just hit "Finalize Order" and you’ll see your total, including a small service charge (because you deserve the best!). 💖
5. **Enjoy Your Meal**: Sit back and relax while we handle your order history, which is always there for you to look back on. 😊

---

## Tech Stack 💻

- **Java Swing**: For a beautiful and responsive graphical user interface.
- **Design Patterns**: MVC for smooth separation of logic and design, and Observer for real-time UI updates.
- **JTextArea**: For showing the order history and total prices.

---

🥳 **Let's Eat!**
Feel free to explore the app, order your favorite food, and enjoy a seamless dining experience from the comfort of your home. 😍💻

---

🌼 Made with love by **Aida, Meruyert, Temutjin** 🌼

