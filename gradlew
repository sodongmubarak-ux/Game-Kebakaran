#!/usr/bin/env bash
set -e

GRADLE_VERSION="8.2"
WRAPPER_DIR="$HOME/.gradle/wrapper/dists"

# Locate existing gradle binary if installed
GRADLE_BIN=$(find "$WRAPPER_DIR" -name gradle -type f -perm /111 2>/dev/null | head -n 1 || true)

if [ -z "$GRADLE_BIN" ] || [ ! -x "$GRADLE_BIN" ]; then
    echo "Downloading Gradle $GRADLE_VERSION binary for build..."
    mkdir -p "$WRAPPER_DIR"
    curl -sSL "https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip" -o "/tmp/gradle.zip"
    unzip -q -o "/tmp/gradle.zip" -d "$WRAPPER_DIR"
    rm -f "/tmp/gradle.zip"
    GRADLE_BIN=$(find "$WRAPPER_DIR" -name gradle -type f -perm /111 | head -n 1)
fi

chmod +x "$GRADLE_BIN"
exec "$GRADLE_BIN" "$@"
