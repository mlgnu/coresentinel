# 🕵️ Coresentinel: A Crime Case Tracking System

This project is a backend system designed for managing and tracking crime cases, including evidence management, user roles, and clearance-based assignments.

## 📦 Main Features

- **User Management**: Support for different user roles, with associated clearance levels.
- **Case Management**: Tracks crime cases and assigns officers based on clearance requirements.
- **Evidence Management**: Stores both textual and image-based evidence, linked to specific cases.
- **Audit Trail**: Every piece of evidence creation is logged with timestamps and user information.

## 🛠️ Setup

1. Clone the repository
2. Run docker compose up
2. Run with Docker

Make sure you have Docker and Docker Compose installed.

Start the application and all required services using Docker Compose: 
      
      docker-compose up --build

4. Configure Google Cloud Storage (For Image Evidence Uploads)

The system integrates with Google Cloud Storage to manage image evidence.
Steps:

  Download GCP Credentials:
  Create a service account in your Google Cloud Console with permissions for Cloud Storage. Download the service account JSON file.

  Set the Environment Variable:
  Point to your credentials file by setting the GOOGLE_APPLICATION_CREDENTIALS environment variable:

    export GOOGLE_APPLICATION_CREDENTIALS=/path/to/your/credentials.json

  spring.cloud.gcp.storage.bucket-name=your-bucket-name should match your bucket name

