//
//  WHMasterViewController.h
//  ModelSample
//
//  Created by Warwick Hunter on 2012-10-30.
//  Copyright (c) 2012 Warwick Hunter. All rights reserved.
//

#import <UIKit/UIKit.h>

@class WHDetailViewController;

@interface WHMasterViewController : UITableViewController

@property (strong, nonatomic) WHDetailViewController *detailViewController;

@end
