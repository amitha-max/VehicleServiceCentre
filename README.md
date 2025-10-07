# 🚗 Vehicle Service Centre Management System

## 📝 Project Overview
The **Vehicle Service Centre Management System** is a Java **console-based application** designed to manage the daily activities of a vehicle service centre.  
It helps track **customers**, their **vehicles**, available **services**, and **billing (invoice generation)** — all through a simple menu-driven interface.

This project demonstrates the use of **Object-Oriented Programming (OOP)**, **Collections**, **Threads**, and **Exception Handling** in Java.

---

## 🎯 Problem Statement
In many service centres, records of customers, vehicles, and service details are maintained manually on paper or spreadsheets.  
This leads to errors, difficulty in tracking customer history, and inefficient billing.  

The aim of this project is to **automate** these operations through a **console-based Java program**.

---

## 👥 Target Users
- Vehicle service centre administrators or staff
- Students learning OOP concepts in Java
- Beginners learning how to use threads, collections, and exception handling

---

## ⚙️ Core Functionalities
1. **Customer Management**
   - Add new customer details (Name, Phone, Email)
   - View all customers in a structured list
   - Manage customer records

2. **Vehicle Management**
   - Add vehicle details for each customer (Vehicle No, Type, Model)
   - View vehicles separately with customer references

3. **Service Management**
   - Add, view, and modify available services
   - Example: Oil Change, Brake Check, Engine Tuning, etc.
   - Each service includes ID, name, description, and cost

4. **Service Processing**
   - Process customer service requests
   - Generate a detailed **invoice** in tabular format

5. **Invoice/Billing System**
   - Display all billing details clearly like:
     ```
     ---------------------------------------
     | Service Name   | Cost               |
     ---------------------------------------
     | Oil Change     | ₹500               |
     | Brake Service  | ₹750               |
     ---------------------------------------
     | Total Amount   | ₹1250              |
     ---------------------------------------
     ```

6. **Multithreading**
   - A **ServiceProcessor Thread** simulates service processing in the background

7. **Exception Handling**
   - Custom `InvalidServiceException` class to handle wrong inputs
   - `try-catch` blocks for invalid menu choices, data handling, etc.

8. **Data Storage (Collections)**
   - **List** → Store all customers and vehicles
   - **Map** → Manage services and their costs
   - **Set** → Ensure no duplicate service names are added

---

## 🧩 Object-Oriented Concepts Used
| Concept | Implementation |
|----------|----------------|
| **Class & Objects** | Classes like `Customer`, `Vehicle`, `Service`, `Transaction`, and `VehicleServiceCentre` |
| **Encapsulation** | Private attributes with public getters/setters |
| **Inheritance** | (Optional extension: e.g., `PremiumCustomer` extends `Customer`) |
| **Polymorphism** | Overridden `toString()` methods to print readable info |
| **Abstraction** | Logical separation between service process and data storage |
| **Exception Handling** | Custom `InvalidServiceException` + try-catch blocks |
| **Multithreading** | `ServiceProcessor` class implementing `Runnable` |
| **Collections** | `ArrayList`, `HashMap`, `HashSet` used for storing data |

---

## 🧠 How It Works
1. When you run the program, a menu appears:

Add Customer
View All Customers
Generate Invoice
Exit

2. You select options to perform tasks.
3. Data (customer, vehicle, service) is stored in memory using Collections.
4. When a service is processed, a Thread starts to simulate real service processing.
5. Invoice is generated after processing and displayed neatly in a table.

---

## 🧵 Threads Example (Simplified)
```java
ServiceProcessor processor = new ServiceProcessor(selectedService);
Thread t = new Thread(processor);
t.start(); // runs service process in background
