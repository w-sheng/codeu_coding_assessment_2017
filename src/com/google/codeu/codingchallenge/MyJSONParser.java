// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.codeu.codingchallenge;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class MyJSONParser implements JSONParser {
	  HashMap<String,MyJSON> JSONMap = new HashMap<String,MyJSON>();
	  HashMap<String,String> StringMap = new HashMap<String,String>();
	  
	  MyJSON JSON = new MyJSON();
	  JSON.JSONMap = JSONMap;
	  JSON.StringMap = StringMap;
		
	  in = in.replaceAll("\\s*}", "}");
	  in = in.replaceAll("\\s*:\\s*", ":");
	  in = in.replaceAll("\\{\\s*", "{");
	  in = in.replaceAll("\\s*,\\s*", ",");
	  
	  LinkedList<Integer> colons = new LinkedList<Integer>();
	  
	  for (int i = 0; i < in.length(); i++) {
		  if (in.charAt(i) == ':') {
			  colons.add(i);
		  }
	  }
	  
	  for (int j = 0; j < colons.size(); j++) {
		  String key = "";
		  String val = "";
		  MyJSON obj = null;
		  
		  int index = colons.get(j);
		  for (int k = 2; k < index; k++) {
			  if (in.charAt(index - k) == '"') {
				  break;
			  }
			  key = key + in.charAt(index - k);
		  }
		  key = new StringBuilder(key).reverse().toString();
		  
		  if (in.charAt(index + 1) == '"') {
			  for (int l = 2; l < in.length() - index; l++) {
				  if (in.charAt(index + l) == '"') {
					  break;
				  }
				  val = val + in.charAt(index + l);
			  }
			  StringMap.put(key, val);
		  } else if (in.charAt(index + 1) == '{') {
			  String objString = "";

			  char[] arr = in.toCharArray();

			  int open = index + 1;
			  int close = closingBrace(arr, open);

			  objString = in.substring(open, close + 1);
			  
			  JSONMap.put(key, (MyJSON) this.parse(objString));
		  } 
	  }
	  
    return JSON;
  }
  
  public int closingBrace(char[] text, int open) {
	  int close = open;
	  int counter = 1;
	  while (counter > 0) {
		  char c = text[++close];
		  if (c == '{') {
			  counter++;
		  } else if (c == '}') {
			  counter--;
		  }
	  }
	  return close;
  }
}
