#!/bin/bash

# Exit immediately on error
set -e

# === Configuration ===
JAR_NAME="mcp-server-0.0.1-SNAPSHOT.jar"
OUTPUT_DIR="target"
DEPENDENCY_DIR="$OUTPUT_DIR/dependency"
MODULES_FILE="$OUTPUT_DIR/modules.txt"
CUSTOM_JRE_DIR="$OUTPUT_DIR/custom-jre"

# === Step 1: Clean and build the project ===
echo "📦 Building project..."
mvn clean package -DskipTests

# === Step 2: Copy runtime dependencies ===
echo "📁 Copying dependencies..."
mvn dependency:copy-dependencies -DoutputDirectory="$DEPENDENCY_DIR"

# === Step 3: Generate module list using jdeps ===
echo "🔍 Analyzing module dependencies..."
jdeps --multi-release 21 \
      --print-module-deps \
      --ignore-missing-deps \
      --class-path "$DEPENDENCY_DIR/*" \
      "$OUTPUT_DIR/$JAR_NAME" > "$MODULES_FILE"

# === Step 4: Create custom JRE with jlink (no sudo needed) ===
echo "🔧 Creating custom JRE..."
JAVA_HOME=$(/usr/libexec/java_home)
"$JAVA_HOME/bin/jlink" \
  --module-path "$JAVA_HOME/jmods" \
  --add-modules "$(cat "$MODULES_FILE")" \
  --output "$CUSTOM_JRE_DIR" \
  --strip-debug \
  --no-header-files \
  --no-man-pages \
  --compress=zip-6

echo "✅ Custom JRE created at $CUSTOM_JRE_DIR"

#Custom JRE can be used to run the application
#target/custom-jre/bin/java -jar target/randstad-search-and-match-api-2.2.0-SNAPSHOT.jar