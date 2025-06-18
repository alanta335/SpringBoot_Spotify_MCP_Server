# Spring Boot Spotify MCP Server

Welcome to the **Spring Boot Spotify MCP Server**! 🚀

This project is a demonstration server built with **Spring Boot** and the **Model Context Protocol (MCP)**, designed to interact with Spotify's API. It provides a hands-on platform for learning about Spring Boot, MCP, and integrating with external music services.

---

## 🌟 Features

- **Spring Boot**: Modern Java framework for rapid application development.
- **Spotify Integration**: Connects to Spotify's API to fetch user data, top tracks, and more.
- **MCP Server**: Implements the Model Context Protocol for structured data exchange.
- **Modular Design**: Clean separation of configuration, service, model, and mapping layers.
- **Learning-Oriented**: Ideal for experimenting with Spring Boot, REST clients, and Spotify integration.

---

## 🗂️ Project Structure

The project follows a standard Maven directory structure:

```plaintext
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.example.mcp_server/
│   │   │       ├── config/         # Spring and REST client configuration
│   │   │       ├── enumeration/    # Enums for playback state, time range, etc.
│   │   │       ├── mapper/         # Map Spotify API responses to internal models
│   │   │       ├── model/          # Domain and response models
│   │   │       ├── service/        # Business logic and Spotify API interaction
│   │   │       └── McpServerApplication.java
│   │   └── resources/
│   │       └── application.yaml    # Application configuration
│   └── test/
│       └── java/
│           └── com.example.mcp_server/
│               └── McpServerApplicationTests.java
├── pom.xml                         # Maven build file
├── README.md                       # Project documentation
```

---

## 🚀 Getting Started

1. **Clone the repository**
2. **Configure Spotify API credentials** in `application.yaml`
3. **Build and run the server**:
   ```sh
   ./mvnw spring-boot:run
   ```
4. **Access the API endpoints** as documented in your controllers/services.

---

## 🛠️ Technologies Used

- Java 17+
- Spring Boot
- Spring Web
- Model Context Protocol (MCP)
- Spotify Web API
- MapStruct (for mapping DTOs)

---

## 📄 License

This project is licensed under the MIT License.

---

## 🤝 Contributing

Contributions are welcome! Please open issues or submit pull requests for improvements.

---

Happy coding! 🎉
