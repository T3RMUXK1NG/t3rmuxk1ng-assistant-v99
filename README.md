# 🤖 RS Assistant v99

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/)
[![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com/)
[![API 26+](https://img.shields.io/badge/API-26%2B-green?style=for-the-badge)](https://developer.android.com/about/dashboards)
[![Build APK](https://github.com/T3RMUXK1NG/t3rmuxk1ng-assistant-v99/actions/workflows/build-apk.yml/badge.svg)](https://github.com/T3RMUXK1NG/t3rmuxk1ng-assistant-v99/actions/workflows/build-apk.yml)

> The previous generation AI voice assistant for Android — featuring NLP-powered Hinglish processing and full phone control. Built by [T3rmuxk1ng](https://youtube.com/@T3rmuxk1ng).

---

## ✨ Features

- 🎤 **Voice Recognition** — Hindi / English / Hinglish multi-language support
- 🧠 **NLP Processing** — Hinglish language processor and intent classifier
- 📷 **Camera Control** — Full camera management
- ✋ **Hand Gesture Recognition** — Gesture-based interactions
- 📱 **Full Phone Control** — Accessibility Service integration
- 🔐 **OAuth Login** — Authentication via Z.AI platform
- 🤖 **AI Chat Integration** — Z.AI powered conversational AI
- 💬 **Conversation UI** — Chat-style interface with message history
- ⚙️ **Settings Activity** — App configuration and preferences
- 🔄 **Boot Receiver** — Auto-start on device boot
- 🎨 **Material Design** — Clean Android UI

---

## 🏗️ Architecture

```
t3rmuxk1ng-assistant-v99/
├── app/src/main/java/com/t3rmuxk1ng/v99/
│   ├── t3rmuxk1ngApp.java           # Application class
│   ├── Constants.java                # App constants
│   ├── ui/
│   │   ├── main/
│   │   │   ├── MainActivity.java     # Main activity with voice UI
│   │   │   └── ConversationAdapter.java # Chat message adapter
│   │   ├── settings/
│   │   │   └── SettingsActivity.java # Settings screen
│   │   └── camera/
│   │       └── CameraActivity.java   # Camera control
│   ├── model/
│   │   └── ConversationMessage.java  # Chat data model
│   ├── nlp/
│   │   ├── HinglishProcessor.java    # Hinglish NLP engine
│   │   └── IntentClassifier.java     # Intent recognition
│   ├── service/
│   │   ├── RSAccessibilityService.java # Phone control service
│   │   ├── AIAssistantService.java     # AI background service
│   │   └── VoiceRecognitionService.java # Voice recognition
│   ├── security/
│   │   └── OAuthManager.java          # Z.AI OAuth
│   ├── util/
│   │   └── PermissionHelper.java      # Permission management
│   └── receiver/
│       └── BootReceiver.java          # Auto-start on boot
├── app/build.gradle
├── build.gradle
└── settings.gradle
```

---

## 📋 Requirements

- **Android 8.0+** (API 26)
- **Permissions**: Microphone, Camera, Phone, Contacts, SMS

---

## 🚀 Installation

### Download APK
Download the latest APK from the [Actions](https://github.com/T3RMUXK1NG/t3rmuxk1ng-assistant-v99/actions) tab.

### Build from Source

```bash
# Clone the repository
git clone https://github.com/T3RMUXK1NG/t3rmuxk1ng-assistant-v99.git
cd t3rmuxk1ng-assistant-v99

# Build debug APK
./gradlew assembleDebug

# APK will be at app/build/outputs/apk/debug/app-debug.apk
```

---

## 🔄 Upgrade to v100

This is v99 — the previous generation. For the latest features including shake detection, memory manager, smart suggestions, and SOS emergency, upgrade to **[RS Assistant v100](https://github.com/T3RMUXK1NG/t3rmuxk1ng-assistant-v100)**.

---

## 📺 YouTube

> Learn how to build AI-powered Android assistants! Watch tutorials on **[T3rmuxk1ng YouTube Channel](https://youtube.com/@T3rmuxk1ng)**

---

## 👤 Author

**T3RMUXK1NG (T3rmuxk1ng)**

- YouTube: [https://youtube.com/@T3rmuxk1ng](https://youtube.com/@T3rmuxk1ng)
- GitHub: [T3RMUXK1NG](https://github.com/T3RMUXK1NG)

---

## 📄 License

This project is licensed under the MIT License.

---

<div align="center">

**If you found this project useful, give it a star!**

[YouTube](https://youtube.com/@T3rmuxk1ng) | [GitHub](https://github.com/T3RMUXK1NG)

</div>
