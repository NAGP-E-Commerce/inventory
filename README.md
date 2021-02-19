# inventory
Inventory Service
docker run -d -p 6033:3306 --name=docker-mysql --env="MYSQL_ROOT_PASSWORD=root" --env="MYSQL_PASSWORD=root" --env="MYSQL_DATABASE=inventory" mysql

docker exec -i docker-mysql mysql -uroot -proot inventory <inventory.sql

docker build -f Dockerfile -t inventory_app .

docker run -t --link docker-mysql:mysql -p 8091:8091 inventory_app 
