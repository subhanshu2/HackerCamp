import os
import time
#from clarifai import rest 
import clarifai
from clarifai.rest import ClarifaiApp


app = ClarifaiApp(api_key='d61baec63bcb4d23a2bf1124c519e095')
model = app.models.get("general-v1.3")

file_dir = os.listdir('./images')

lis=[]
for i in file_dir:
	# print(i)
	data = model.predict_by_filename('./images/'+i)
# print (data)
	for i in (((data['outputs'][0])['data'])['concepts']):
		if i['name']=='accident':
			lis.append(i['name'])

if 'accident' in lis:
	print("Accident Alert")

else:
	print("Nothing")