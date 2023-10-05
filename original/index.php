<?php
/* 
 * This code is a demonstration of an algorithm I worte
 * and open the template in the editor.
 */
include 'AutoGroup.inc.php';

$autoGroupObject = new AutoGroup();

$employees = array(
  0 => 'Mark is an accountant and is in charge of money',
  1 => 'Jaqueline is a designer, she creates graphics',
  2 => 'Sayf is a programmer, he writes software',
  3 => 'Eddy is the manager of software development',
  4 => 'Judith writes tutorials for the software products',
  5 => 'Bob is an account manager, he manages customers accounts',
  6 => 'Quint is also in the graphics department and he makes icons',
  7 => 'Roxan is the cleaning lady, we would be lost without her'
);

echo "<PRE>Original array of employees \n";
print_r($employees);

echo "\nGrouped employees after pattern analysis\n";
print_r($autoGroupObject->autoGroupSentences($employees));
?>
