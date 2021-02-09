#!/bin/bash
echo "Packing the Producer Application"
gradle clean producer:bootBuildImage --imageName=producer:latest

echo "Packing the Consumer Application "
gradle clean consumer:bootBuildImage --imageName=consumer:latest
