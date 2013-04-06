#
# Copyright Warwick Hunter 2013. All rights reserved.
#
# Demonstration of how to use Google Protocol Buffers.
# 
# @author Warwick Hunter (w.hunter@computer.org)
# @date   2013-04-06
#
import sys

sys.path.append("gen/protobuf")

import photos_v1_pb2

album = photos_v1_pb2.Album()
album.name = "Warwick in Modena"
album.privacy = photos_v1_pb2.PUBLIC

picture = album.picture.add()
picture.description = "War Duck statue in Modena"
picture.timestamp = "2013-04-06 13:00:00"
img = open("images/statue.jpg")
picture.image = img.read()[:20]
img.close()
picture.imageMimeType = "image/jpg"
picture.latitude = 44.6500
picture.longitude = 10.9167


f = open("album.dat", "wb")
f.write(album.SerializeToString())
f.close();
        
f = open("album.dat", "rb")
new_album = photos_v1_pb2.Album()
new_album.ParseFromString(f.read())
f.close()

print new_album
