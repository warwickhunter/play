//
//  WHModel.m
//
//  This is an example of a Model class from the design pattern
//  Model View Controller. It's a singleton that collects data
//  from multiple views in the application.
//
//  Created by Warwick Hunter on 2012-10-30.
//  Copyright (c) 2012 Warwick Hunter. All rights reserved.
//

#import "WHModel.h"

static WHModel* s_instance = nil;

@implementation WHModel

@synthesize items;

-(id)init {
    self = [super init];
    if (self) {
        items = [NSMutableArray arrayWithCapacity:10];
    }
    return self;
}

/**
 * A static method to access the singleton model
 */
+(WHModel*) instance
{
    if (s_instance == nil) {
        s_instance = [[WHModel alloc] init];
    }
    return s_instance;
}

@end
