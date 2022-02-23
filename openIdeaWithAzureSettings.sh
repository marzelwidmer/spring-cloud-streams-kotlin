#!/bin/bash
set -a

export AZ_RESOURCE_GROUP=Demo
export AZ_RESOURCE_LOCATION=westus
export AZ_SERVICE_BUS_NAMESPACE=kboot-namespace

export AZ_SERVICE_BUS_CONNECTION_STRING=$(az servicebus namespace authorization-rule keys list \
--resource-group $AZ_RESOURCE_GROUP \
--namespace-name $AZ_SERVICE_BUS_NAMESPACE \
--name RootManageSharedAccessKey | jq '.primaryConnectionString' | sed 's:^.\(.*\).$:\1:')

idea .
