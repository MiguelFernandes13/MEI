# Path: benchmark/clients.sh

clients=(1 2 4 8 12 16 20)
for c in "${clients[@]}" ; do
    echo "Running $c clients"
    java -jar target/benchmark-1.0-SNAPSHOT.jar -d jdbc:postgresql://localhost/testdb -U postgres -P postgres -p -x -c $c
done