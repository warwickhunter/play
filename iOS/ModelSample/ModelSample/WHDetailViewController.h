//
//  WHDetailViewController.h
//  ModelSample
//
//  Created by Warwick Hunter on 2012-10-30.
//  Copyright (c) 2012 Warwick Hunter. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface WHDetailViewController : UIViewController <UISplitViewControllerDelegate>

@property (strong, nonatomic) id detailItem;

@property (weak, nonatomic) IBOutlet UILabel *detailDescriptionLabel;
@end
