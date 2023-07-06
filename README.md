# k-challenge
NY cabs trips

# Build
git clone git@github.com:feliciovaz/k-challenge.git
cd k-challenge
mvn clean package
docker build -t challenge-1.0.0.jar .
docker compose up

# Curl commands

## Upload trips
curl --location 'http://localhost:8080/load-trips' --form 'file=@"full_file_path"'

## Upload Zones
curl --location 'http://localhost:8080/load-zones' --form 'file=@"full_file_path"'

## Top Zones
curl --location 'http://localhost:8080/top-zones?order=pickups'
curl --location 'http://localhost:8080/top-zones?order=dropoffs'

## Zone trips
curl --location 'http://localhost:8080/zone-trips?date=2018-01-01&zone=Central%20Harlem%20North'

