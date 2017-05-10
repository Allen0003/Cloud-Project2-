/* 
 * This file defines the mapreduce function to sum up the traffic accidents happened in the certain area of Melbourne
 * Use database black_spots_2007-2012_vic
 * Authers: Team 10
 *      Ziren Wang, zirenw@student.unimelb.edu.au, 720128
 *      Xiang Xiang, xxiang2@student.unimelb.edu.au, 720138
 *      Yongchul Kim, yongchulk@student.unimelb.edu.au, 750659
 *      Own Daghagheleh, odaghagheleh@student.unimelb.edu.au, 816273
 *      Wen Pin Wu, w.wu47@student.unimelb.edu.au, 871702
 */


//name:mel_b1, map: Get traffic accidents in B1, reduce: _sum
function (doc) {
   
   var co = doc.geometry.coordinates;
   var longitude = co[0];
   var latitude  = co[1];
   var accident  = parseInt(doc.accidents);
   
   
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;


   if(longitude>x1 && longitude<=x2 && latitude>=y3 && latitude<y2 ){
       emit(doc.geometry.type,accident);
   }

}
//name:mel_b2, map: Get traffic accidents in B2, reduce: _sum
function (doc) {
   
   var co = doc.geometry.coordinates;
   var longitude = co[0];
   var latitude  = co[1];
   var accident  = parseInt(doc.accidents);
   
   
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;


   if(longitude>x2 && longitude<=x3 && latitude>=y3 && latitude<y2 ){
       emit(doc.geometry.type,accident);
   }

}
//name:mel_b3, map: Get traffic accidents in B3, reduce: _sum
function (doc) {
   
   var co = doc.geometry.coordinates;
   var longitude = co[0];
   var latitude  = co[1];
   var accident  = parseInt(doc.accidents);
   
   
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;


   if(longitude>x3 && longitude<=x4 && latitude>=y3 && latitude<y2 ){
       emit(doc.geometry.type,accident);
   }

}
//name:mel_b4, map: Get traffic accidents in B4, reduce: _sum
function (doc) {
   
   var co = doc.geometry.coordinates;
   var longitude = co[0];
   var latitude  = co[1];
   var accident  = parseInt(doc.accidents);
   
   
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;


   if(longitude>x3 && longitude<=x4 && latitude>=y3 && latitude<y2 ){
       emit(doc.geometry.type,accident);
   }

}
//name:mel_c1, map: Get traffic accidents in C1, reduce: _sum
function (doc) {
   
   var co = doc.geometry.coordinates;
   var longitude = co[0];
   var latitude  = co[1];
   var accident  = parseInt(doc.accidents);
   
   
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;


   if(longitude>x1 && longitude<=x2 && latitude>=y4 && latitude<y3 ){
       emit(doc.geometry.type,accident);
   }

}
//name:mel_c2, map: Get traffic accidents in C2, reduce: _sum
function (doc) {
   
   var co = doc.geometry.coordinates;
   var longitude = co[0];
   var latitude  = co[1];
   var accident  = parseInt(doc.accidents);
   
   
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;


   if(longitude>x2 && longitude<=x3 && latitude>=y4 && latitude<y3 ){
       emit(doc.geometry.type,accident);
   }

}
//name:mel_c3, map: Get traffic accidents in C3, reduce: _sum
function (doc) {
   
   var co = doc.geometry.coordinates;
   var longitude = co[0];
   var latitude  = co[1];
   var accident  = parseInt(doc.accidents);
   
   
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;


   if(longitude>x3 && longitude<=x4 && latitude>=y4 && latitude<y3 ){
       emit(doc.geometry.type,accident);
   }

}
//name:mel_c4, map: Get traffic accidents in C4, reduce: _sum
function (doc) {
   
   var co = doc.geometry.coordinates;
   var longitude = co[0];
   var latitude  = co[1];
   var accident  = parseInt(doc.accidents);
   
   
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;


   if(longitude>x4 && longitude<=x5 && latitude>=y4 && latitude<y3 ){
       emit(doc.geometry.type,accident);
   }

}
//name:mel_c5, map: Get traffic accidents in C5, reduce: _sum
function (doc) {
   
   var co = doc.geometry.coordinates;
   var longitude = co[0];
   var latitude  = co[1];
   var accident  = parseInt(doc.accidents);
   
   
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;


   if(longitude>x5 && longitude<=x6 && latitude>=y4 && latitude<y3 ){
       emit(doc.geometry.type,accident);
   }

}
//name:mel_d3, map: Get traffic accidents in D3, reduce: _sum
function (doc) {
   
   var co = doc.geometry.coordinates;
   var longitude = co[0];
   var latitude  = co[1];
   var accident  = parseInt(doc.accidents);
   
   
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;


   if(longitude>x3 && longitude<=x4 && latitude>=y5 && latitude<y4 ){
       emit(doc.geometry.type,accident);
   }

}
//name:mel_d4, map: Get traffic accidents in D4, reduce: _sum
function (doc) {
   
   var co = doc.geometry.coordinates;
   var longitude = co[0];
   var latitude  = co[1];
   var accident  = parseInt(doc.accidents);
   
   
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;


   if(longitude>x4 && longitude<=x5 && latitude>=y5 && latitude<y4 ){
       emit(doc.geometry.type,accident);
   }

}
//name:mel_d5, map: Get traffic accidents in D5, reduce: _sum
function (doc) {
   
   var co = doc.geometry.coordinates;
   var longitude = co[0];
   var latitude  = co[1];
   var accident  = parseInt(doc.accidents);
   
   
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;


   if(longitude>x5 && longitude<=x6 && latitude>=y5 && latitude<y4 ){
       emit(doc.geometry.type,accident);
   }

}