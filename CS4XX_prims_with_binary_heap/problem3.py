#!/usr/bin/python3

import math
import decimal
import re

locations={}
links={}
graph={}
city_heaps={}

class prims_node:
	def __init__(self, node):
		self.node = [node]
		self.routes = []

	def join(self,node,route):
		self.node += node.node
		self.routes = self.routes + node.routes + [route]
		return self

	def find(self,node):
		if node in self.node:
			return True
		else:
			return False

class prims_box:
	def __init__(self):
		self.data = []

	def is_mst(self):
		if len(self.data) == 1:
			return True
		else:
			return False

	def insert(self, node):
		self.data.append(node)

	def make_path(self,path):
		source = path.source
		dest = path.dest
		source_found = None
		dest_found = None
		for node in self.data:
			if node.find(source):
				source_found = node
			if node.find(dest):
				dest_found = node
		if source_found != dest_found:
			self.data.remove(source_found)
			self.data.remove(dest_found)
			self.data.append(source_found.join(dest_found,path))
			return True
		else:
			return False

class element:
	def __init__(self,source,dest,distance):
		self.distance = distance
		self.source = source
		self.dest = dest

	def __lt__(self, other):
		return self.distance < other

	def __gt__(self, other):
		return self.distance > other

	def __ne__(self,other):
		return self.distance != other

	def __str__(self):
		return str("From: " + self.source + " To: " + self.dest + " Is: " + str(self.distance) + " KM")

class heap:
	def __init__(self):
		self.data = []

	def empty(self):
		if len(self.data) == 0:
			return True
		else:
			return False

	def insert(self,element):
		self.data.append(element)
		index = len(self.data) - 1
		if index == 0:
			return
		parent = (index-1)//2
		while self.data[parent] > self.data[index] and index != 0:
			self.data[parent], self.data[index] = self.data[index],self.data[parent]
			index = parent
			parent = (index-1)//2

	def extract(self):
		if len(self.data) == 1:
			to_return = self.data[0]
			self.data = []
		else:
			to_return = self.data[0]
			self.data[0] = self.data.pop()
			self.min_heapify(0)
		return to_return

	def min_heapify(self,index):
		left = index * 2 + 1
		right = index * 2 + 2
		smallest = index
		if left < len(self.data) and self.data[left] < self.data[smallest]:
			smallest = left
		if right < len(self.data) and self.data[right] < self.data[smallest]:
			smallest = right
		if self.data[smallest] != self.data[index]:
			self.data[smallest],self.data[index] = self.data[index],self.data[smallest]
			self.min_heapify(smallest)

	def str(self,index):
		if index >= len(self.data):
			return ""
		else:
			return "[Current: " + str(self.data[index]) + " (Left: " + self.str(index*2+1) + "Right: " + self.str(index*2+2) + " ) ]"

def convert_to_dec(Coor):
	DD = decimal.Decimal(Coor[0]) + decimal.Decimal(Coor[1])/60 + decimal.Decimal(Coor[2])/3600
	if Coor[3] == "W" or Coor[3] == "S":
		DD *= -1
	return DD

def calculate_distance(start,end):
	dlon = convert_to_dec(start[4::]) - convert_to_dec(end[4::])
	lat2 = convert_to_dec(start[0:4]) 
	lat1 =  convert_to_dec(end[0:4])
	dlat = lat2 - lat1
	a = (math.sin(math.radians(dlat/2)))**2 + math.cos(math.radians(lat1)) * math.cos(math.radians(lat2)) * (math.sin(math.radians(dlon/2))**2)
	c = 2 * (math.atan2(math.sqrt(a),math.sqrt(1-a)))
	return 6371.000 * c # IN KM

def parse_locations():
	f = open("problem_3/hawaii.txt",'r')
	for line in f:
		line = line.strip("\n").split(",")
		city = line[0]
		coordinates = [l.replace(" ","") for l in line[1::]]
		locations[city] = coordinates
	f.close()

def parse_links():
	for island in locations:
		for connection in locations:
			if island not in links:
				links[island] = [connection]
			else:
				links[island].append(connection)

def make_graph():
	for city in locations:
		graph[city] = {}
		for subcity in locations:
			graph[city][subcity] = 0
	for city in links:
		neap = heap()
		start_coordinates = locations[city]
		for link in links[city]:
			if graph[city][link] != 0: # Then I've changed it
				continue
			end_coordinates = locations[link]
			dist = calculate_distance(start_coordinates,end_coordinates)
			neap.insert(element(city,link,dist))
		city_heaps[city] = neap

def output_graph(routes):
	f = open("problem_3/mst_hawaii.dot",'w')
	f.write("\tdigraph finite_state_machine { \n")
	f.write("\trankdir=LR;\n")
	f.write('\tsize="100,100"\n')
	f.write("\tnode [shape = doublecircle];")
	for city in locations:
		f.write(" " + re.sub('\W','_',city))
	f.write(";\n")
	f.write("\tnode [shape = circle];\n")
	for route in routes:
		f.write("\t" + re.sub('\W','_',route.source) + " -> " + re.sub('\W','_',route.dest) + ' [ label = "' + str(route.distance) + '" ];\n')
	f.write("}")
	f.close()

parse_locations()
parse_links()
make_graph()

box = prims_box()
for city in locations:
	box.insert(prims_node(city))

while not box.is_mst():
	for node in box.data[0].node:
		path = city_heaps[node].extract()
		if path.source not in box.data[0].node and path.dest not in box.data[0].node:
			while not city_heaps[path.dest].empty():
				city_heaps[path.source].insert(city_heaps[path.dest].extract())
			city_heaps[path.dest] = city_heaps[path.source]
			break
	box.make_path(path)

routes = box.data[0].routes
output_graph(routes)
