# LangChain4J pdf Resume RAG, Spring Boot, and AstraDB

This repository contains the **PDF Assistant** application, which answers questions based on the information in a provided PDF. It uses **LangChain4J** for querying OpenAI models (or other LLMs), **AstraDB** for storing embeddings, and **Spring Boot** to create a REST API. The entry point of the Spring Boot application is configured to use the name of your CV PDF.

## Technologies

- **LangChain4J**: Framework for LLM interaction.
- **Spring Boot**: Backend framework for REST APIs, configured to use the name of your CV PDF as the entry point.
- **AstraDB**: Cloud database for storing embeddings, connected to Cassandra for data storage.
- **Groq Cloud**: AI model inference with optimized compute power.

## Features

- **PDF-based Question Answering**: Extracts information from PDFs to answer queries.
- **RAG Model**: Combines language models with embeddings stored in AstraDB for context-aware responses.
- **OpenAI and Groq Cloud Models**: Use either OpenAI's models or Groq Cloud with Llama3 for enhanced performance.

## Getting Started

### Prerequisites

- **Java 21+**
- **Maven**
- **AstraDB and Groq Cloud Accounts**

### Usage

1. Add the PDF under the `resources` folder for the app to pick it up from the classpath. Update the spring boot entry point java file with the name of the PDF.
2. You can easily access the simple UI by navigating to `localhost:8080/index.html`, or alternatively, use Postman or any other preferred technology to interact with the application via HTTP.