#!/bin/bash
echo "Packing the Processor Application"
gradle clean processor:bootBuildImage --imageName=processor:latest

echo "Packing the Sink Application "
gradle clean sink:bootBuildImage --imageName=sink:latest

echo "Packing the Source Application"
gradle clean source:bootBuildImage --imageName=source:latest
