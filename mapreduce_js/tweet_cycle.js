function (doc) {
   var str = doc.text.toLowerCase();
   var cycles = ["cyclist","cycling","cycle","helmet","bike","riding","ride","rider","bicycle","pedaling","pedal","road ride","mtb","push bike"];
   var i,x;

   for(i=0;i<cycles.length;i++){
     x=str.indexOf(cycles[i]);
     if(x!=-1){
       emit(doc.ccc_city,doc.text);
     }
   }
}

function (doc) {
  var co = doc.coordinates.coordinates;
  var longitude = co[0];
  var latitude = co[1];
  if(longitude>=144 && latitude<=-37)
     emit(doc.ccc_city, [longitude,latitude]);
}

function (doc) {
   var str = doc.text.toLowerCase();
   var cycles = ["cyclist","cycling","cycle","helmet","bike","riding","ride","rider","bicycle","pedaling","pedal","road ride","mtb","push bike"];
   var i,x;
   
   var co = doc.coordinates.coordinates;
   var longitude = co[0];
   var latitude  = co[1];
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;

   for(i=0;i<cycles.length;i++){
     x=str.indexOf(cycles[i]);
     if(x!=-1 && longitude>x1 && longitude<=x2 && latitude>=y4 && latitude<y3 ){
       emit(doc.ccc_city,doc.text);
     }
   }
}


function (doc) {
  var co = doc.coordinates.coordinates;
  var longitude = co[0];
  var latitude = co[1];
  if(longitude>=144 && latitude<=-37)
     emit(doc.ccc_city, [longitude,latitude]);
}


function (doc) {
   var str = doc.text.toLowerCase();
   var cycles = ["cyclist","cycling","cycle","helmet","bike","riding","ride","rider","bicycle","pedaling","pedal","road ride","mtb","push bike"];
   var i,x;
   
   var co = doc.coordinates.coordinates;
   var longitude = co[0];
   var latitude  = co[1];
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;

   for(i=0;i<cycles.length;i++){
     x=str.indexOf(cycles[i]);
     if(x!=-1 && longitude>x1 && longitude<=x2 && latitude>=y2 && latitude<y1 ){
       emit(doc.ccc_city,doc.text);
     }
   }
}



function (doc) {
  emit(doc.ccc_city, 1);
}

function (doc) {
   var str = doc.text.toLowerCase();
   var cycles = ["cyclist","cycling","cycle","helmet","bike","riding","ride","rider","bicycle","pedaling","pedal","road ride","mtb","push bike"];
   var i,x;

   for(i=0;i<cycles.length;i++){
     x=str.indexOf(cycles[i]);
     if(x!=-1){
       emit(doc.ccc_city,doc.text);
     }
   }
}

function (doc) {
   var str = doc.text.toLowerCase();
   var cycles = ["cyclist","cycling","cycle","helmet","bike","riding","ride","rider","bicycle","pedaling","pedal","road ride","mtb","push bike"];
   var i,x;
   
   var co = doc.coordinates.coordinates;
   var longitude = co[0];
   var latitude  = co[1];
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;

   for(i=0;i<cycles.length;i++){
     x=str.indexOf(cycles[i]);
     if(x!=-1 && longitude>x1 && longitude<=x2 && latitude>=y4 && latitude<y3 ){
       emit(doc.ccc_city,doc.text);
     }
   }
}



