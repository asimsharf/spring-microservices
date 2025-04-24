#!/bin/bash

DEBUG_MODE=false
ACTION="start"

# Ports to match for each service (used for stopping)
PORTS=(8888 8761 8080 9001)

# Check flags and commands
if [[ "$1" == "--debug" ]]; then
  DEBUG_MODE=true
  shift
fi

if [[ "$1" == "stop" || "$1" == "restart" || "$1" == "reload" ]]; then
  ACTION="$1"
fi

function stop_services() {
  echo "🛑 Stopping all running microservices..."
  for PORT in "${PORTS[@]}"; do
    PID=$(lsof -ti tcp:$PORT)
    if [[ -n "$PID" ]]; then
      echo "🔪 Killing process on port $PORT (PID: $PID)"
      kill -9 $PID
    else
      echo "✅ No service running on port $PORT"
    fi
  done
}

function start_service() {
  local service_path=$1
  local service_name=$2
  local debug_port=$3

  local run_command="./mvnw spring-boot:run"

  if $DEBUG_MODE; then
    run_command="$run_command -Dspring-boot.run.jvmArguments='-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:$debug_port'"
  fi

  osascript <<EOF
    tell application "Terminal"
      do script "cd $(pwd)/$service_path && $run_command"
      set custom title of front window to "$service_name"
    end tell
EOF

  echo "🚀 Launching $service_name... (Debug: $DEBUG_MODE)"
  sleep 4
}

# STOP
if [[ "$ACTION" == "stop" ]]; then
  stop_services
  echo "✅ All services stopped."
  exit 0
fi

# RESTART
if [[ "$ACTION" == "restart" ]]; then
  stop_services
  sleep 2
fi

# START/RELOAD
echo "💻 Starting microservices..."

start_service "config-server" "Config Server" 5005
start_service "discovery-server" "Discovery Server" 5006
start_service "api-gateway" "API Gateway" 5007
start_service "user-service" "User Service" 5008

echo ""
echo "✅ All services launched!"
echo ""

echo "🔍 You can verify services here:"
echo "🧩 Eureka Dashboard:      http://localhost:8761"
echo "⚙️  Config Server:         http://localhost:8888/user-service/default"
echo "📦 API Gateway:           http://localhost:8080"
echo "👤 User Service (health): http://localhost:9001/actuator/health"
echo ""
if $DEBUG_MODE; then
  echo "🐞 Debug mode active on ports:"
  echo "   Config Server:     5005"
  echo "   Discovery Server:  5006"
  echo "   API Gateway:       5007"
  echo "   User Service:      5008"
  echo ""
fi
echo "🧪 Run this to test User Service:"
echo "   curl http://localhost:9001/actuator/health"
echo ""
