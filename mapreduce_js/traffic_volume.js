/* 
 * This file defines the mapreduce function to sum up the traffic volume in the certain area of Melbourne
 * Use database victorian_road_traffic_volumes
 * Authers: Team 10
 *      Ziren Wang, zirenw@student.unimelb.edu.au, 720128
 *      Xiang Xiang, xxiang2@student.unimelb.edu.au, 720138
 *      Yongchul Kim, yongchulk@student.unimelb.edu.au, 750659
 *      Own Daghagheleh, odaghagheleh@student.unimelb.edu.au, 816273
 *      Wen Pin Wu, w.wu47@student.unimelb.edu.au, 871702
 */


//name:vol_b1, map: Get traffic volume in B1, reduce: _sum
function (doc) {
   
   var co = doc.geometry.coordinates;
   var cos = co[0];
   var coss = cos[0];
   var longitude = coss[0];
   var latitude  = coss[1];
   var veh = parseInt(doc.allvehs);
   
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;


   if(longitude>x1 && longitude<=x2 && latitude>=y3 && latitude<y2 ){
       emit(doc.geometry.type,veh);
   }

}
//name:vol_b2, map: Get traffic volume in B2, reduce: _sum
function (doc) {
   
   var co = doc.geometry.coordinates;
   var cos = co[0];
   var coss = cos[0];
   var longitude = coss[0];
   var latitude  = coss[1];
   var veh = parseInt(doc.allvehs);
   
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;


   if(longitude>x2 && longitude<=x3 && latitude>=y3 && latitude<y2 ){
       emit(doc.geometry.type,veh);
   }

}
//name:vol_b3, map: Get traffic volume in B3, reduce: _sum
function (doc) {
   
   var co = doc.geometry.coordinates;
   var cos = co[0];
   var coss = cos[0];
   var longitude = coss[0];
   var latitude  = coss[1];
   var veh = parseInt(doc.allvehs);
   
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;


   if(longitude>x3 && longitude<=x4 && latitude>=y3 && latitude<y2 ){
       emit(doc.geometry.type,veh);
   }

}
//name:vol_b4, map: Get traffic volume in B4, reduce: _sum
function (doc) {
   
   var co = doc.geometry.coordinates;
   var cos = co[0];
   var coss = cos[0];
   var longitude = coss[0];
   var latitude  = coss[1];
   var veh = parseInt(doc.allvehs);
   
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;


   if(longitude>x4 && longitude<=x5 && latitude>=y3 && latitude<y2 ){
       emit(doc.geometry.type,veh);
   }

}
//name:vol_c1, map: Get traffic volume in C1, reduce: _sum
function (doc) {
   
   var co = doc.geometry.coordinates;
   var cos = co[0];
   var coss = cos[0];
   var longitude = coss[0];
   var latitude  = coss[1];
   var veh = parseInt(doc.allvehs);
   
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;


   if(longitude>x1 && longitude<=x2 && latitude>=y4 && latitude<y3 ){
       emit(doc.geometry.type,veh);
   }

}
//name:vol_c2, map: Get traffic volume in C2, reduce: _sum
function (doc) {
   
   var co = doc.geometry.coordinates;
   var cos = co[0];
   var coss = cos[0];
   var longitude = coss[0];
   var latitude  = coss[1];
   var veh = parseInt(doc.allvehs);
   
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;


   if(longitude>x2 && longitude<=x3 && latitude>=y4 && latitude<y3 ){
       emit(doc.geometry.type,veh);
   }

}
//name:vol_c3, map: Get traffic volume in C3, reduce: _sum
function (doc) {
   
   var co = doc.geometry.coordinates;
   var cos = co[0];
   var coss = cos[0];
   var longitude = coss[0];
   var latitude  = coss[1];
   var veh = parseInt(doc.allvehs);
   
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;


   if(longitude>x3 && longitude<=x4 && latitude>=y4 && latitude<y3 ){
       emit(doc.geometry.type,veh);
   }

}
//name:vol_c4, map: Get traffic volume in C4, reduce: _sum
function (doc) {
   
   var co = doc.geometry.coordinates;
   var cos = co[0];
   var coss = cos[0];
   var longitude = coss[0];
   var latitude  = coss[1];
   var veh = parseInt(doc.allvehs);
   
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;


   if(longitude>x4 && longitude<=x5 && latitude>=y4 && latitude<y3 ){
       emit(doc.geometry.type,veh);
   }

}
//name:vol_c5, map: Get traffic accidents in C5, reduce: _sum
function (doc) {
   
   var co = doc.geometry.coordinates;
   var cos = co[0];
   var coss = cos[0];
   var longitude = coss[0];
   var latitude  = coss[1];
   var veh = parseInt(doc.allvehs);
   
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;


   if(longitude>x5 && longitude<=x6 && latitude>=y4 && latitude<y3 ){
       emit(doc.geometry.type,veh);
   }

}
//name:vol_d3, map: Get traffic volume in D3, reduce: _sum
function (doc) {
   
   var co = doc.geometry.coordinates;
   var cos = co[0];
   var coss = cos[0];
   var longitude = coss[0];
   var latitude  = coss[1];
   var veh = parseInt(doc.allvehs);
   
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;


   if(longitude>x3 && longitude<=x4 && latitude>=y5 && latitude<y4 ){
       emit(doc.geometry.type,veh);
   }

}
//name:vol_d4, map: Get traffic volume in D4, reduce: _sum
function (doc) {
   
   var co = doc.geometry.coordinates;
   var cos = co[0];
   var coss = cos[0];
   var longitude = coss[0];
   var latitude  = coss[1];
   var veh = parseInt(doc.allvehs);
   
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;


   if(longitude>x4 && longitude<=x5 && latitude>=y5 && latitude<y4 ){
       emit(doc.geometry.type,veh);
   }

}
//name:vol_d5, map: Get traffic volume in D5, reduce: _sum
function (doc) {
   
   var co = doc.geometry.coordinates;
   var cos = co[0];
   var coss = cos[0];
   var longitude = coss[0];
   var latitude  = coss[1];
   var veh = parseInt(doc.allvehs);
   
   var x1=144.7, x2=144.85, x3=145,   x4=145.15, x5=145.30, x6=145.45;
   var y1=-37.5, y2=-37.65, y3=-37.8, y4=-37.95, y5=-38.1;


   if(longitude>x5 && longitude<=x6 && latitude>=y5 && latitude<y4 ){
       emit(doc.geometry.type,veh);
   }

}