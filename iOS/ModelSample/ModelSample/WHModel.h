//
//  WHModel.h
//
//  This is an example of a Model class from the design pattern
//  Model View Controller. It's a singleton that collects data
//  from multiple views in the application.
//
//  Created by Warwick Hunter on 2012-10-30.
//  Copyright (c) 2012 Warwick Hunter. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface WHModel : NSObject

@property (strong, atomic, readwrite) NSMutableArray* items;

/**
 * A static method to access the singleton model
 */
+(WHModel*) instance;

@end

