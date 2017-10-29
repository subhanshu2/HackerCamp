import numpy as np
import cv2
#cap = cv2.VideoCapture(0)
cap = cv2.VideoCapture('http://10.10.1.198:4747/mjpegfeed?640x480')
count = 0

while True:
	# Capture frame-by-frame
   	ret, frame = cap.read()
  

   	# Our operations on the frame come here
   	gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
   	# save frame as JPEG file
   	name = "frame%d.jpg"%count
   	cv2.imwrite(name, frame) 
   	count +=1

   	# Display the resulting frame
   	cv2.imshow('frame',gray)
   	if cv2.waitKey(10) != -1:
   		break