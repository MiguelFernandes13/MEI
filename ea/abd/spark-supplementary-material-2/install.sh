#!/bin/bash

# Install docker
sudo apt update
sudo apt-get install ca-certificates curl gnupg lsb-release -y
sudo mkdir -p /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt-get update
sudo apt-get install docker-ce docker-ce-cli containerd.io docker-compose-plugin docker-compose -y

# Download the dataset
wget https://storage.googleapis.com/abd23/imdb.zip
unzip imdb.zip
mv imdb app
rm imdb.zip

# Set run.sh permissions
sudo chmod +x run.sh

# Docker without sudo
sudo usermod -aG docker $USER
newgrp docker
