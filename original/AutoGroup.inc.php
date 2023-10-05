<?php

//
// +---------------------------------------------------------------------------+
// | MediaInvent                                                               |
// +---------------------------------------------------------------------------+
// | Copyright (c) 2010 MediaInvent                                            |
// +---------------------------------------------------------------------------+
// | This source file is copyrighted by MediaInvent, the Netherlands. If you   |
// | would like to use this file in your projects, please contact us by e-mail |
// | at info@mediainvent.nl                                                    |
// +---------------------------------------------------------------------------+
// | Authors: Sayf Jawad                                                       |
// +---------------------------------------------------------------------------+
//
// $Id: String.inc.php 19468 2010-10-28 14:39:00Z eddy $
//

/**
 * This file contains the Session functions that can be used by all modules
 *
 * @author			Sayf Jawad
 * @copyright		2010, MediaInvent
 * @version			$Revision: 1.2
 * @package			Libraries
 */
class AutoGroup {

  /**
   * Constructor
   */
  function AutoGroup() {
    // does nothing at the moment
  }

  /**
   * This function recieves an array of sentences and searches for
   * a relation between the recieved sentences. Related sentences
   * will be grouped when relation between two or more sentences
   * are found. Sentences that cannot be grouped shall be returned
   * as they were recieved.
   *
   * @param array $List                     Contains a list of the elements that need to be grouped
   * @param int   $MinimalDenominatorLength The minimal length of the denominator string used to find relations
   * @param int   $MinimumGroupSize         A group of found sentences is conciderd as a group if there count >= this variable
   *
   * @return array Multidimensional array that may contain sentences grouped by a commonDenominator & original ungrouped sentences
   */
  public function autoGroupSentences(array $List, $MinimalDenominatorLength = 4, $MinimumGroupSize = 2) {

    if ($this->checkArray($List)) {
      $PossibleDenominators = Array();
      $GroupsFound = Array();
      $Groups = Array();
      $RemovedElements = Array();
      $RetryList = $List;

      for ($i = 0; $i < count($List); $i++) {
        //Generate all possible key's with a minimum keyLengt
        $PossibleDenominators = $this->_getKeyList($RetryList, $MinimalDenominatorLength);
        //check the grouping capability for each key and remove useless keys
        $UniqueDenominators = $this->_getValidKeys($PossibleDenominators, $RetryList, $MinimumGroupSize);
        //search for Longest keys that can group elements from InputList
        $LongesteDenominators = $this->_getLongestKeys($UniqueDenominators);
        //add the new found groups to a list using the keys
        $GroupsFound[] = $this->_getGroup($LongesteDenominators, $RetryList);
        //exclude grouped elements from next grouping search
        $UsedDenominators = $this->_getExclusions($GroupsFound);
        //Remove already grouped Elements from List
        $RetryList = $this->_getNewList($RetryList, $UsedDenominators);

        foreach ($GroupsFound AS $Grouped) {
          foreach ($Grouped AS $Element) {
            foreach ($Element AS $Index => $SubElement) {
              $RemovedElements[$Index] = $List[$Index];
            }
          }
        }

        if (count($RemovedElements) == count($List)) {
          break;
        }
      }
      //format groups found for Response
      foreach ($GroupsFound AS $Element) {
        foreach ($Element AS $GroupKey => $SubElement) {
          $Groups[$GroupKey] = $SubElement;
        }
      }

      //Add UnGroupable Sentences to the Response
      $Response = $this->_addUngroupable($List, $Groups, $RemovedElements);
    } else {
      // No array was provided
      $Response = FALSE;
    }

    return $Response;
  }

  /**
   * This function recieves an array of sentences, it then extract
   * and returns all possible denominator's in an array
   *
   * @param array $List              An array of sentences
   * @param int   $DenominatorLength The accepted minimum length for a denominator
   *
   * @see					autoGroupSentence
   *
   * @return array This array contains all unique possible denominators
   */
  private function _getKeyList($List, $DenominatorLength) {
    $MinKeyLength = 4;
    foreach ($List as $Sentence) {
      if ($DenominatorLength > strlen($Sentence)){
        $DenominatorLength = $MinKeyLength;
      }
    }

    $Sentence = '';
    $Keys = Array();

    foreach ($List as $Sentence) {
      for ($AddedLength = 0; $AddedLength < strlen($Sentence); $AddedLength++) {
        for ($i = 0; $i < strlen($Sentence) - $DenominatorLength; $i++) {
          $Keys[] = trim(substr($Sentence, $i, $DenominatorLength + $AddedLength));
        }
      }
    }

    foreach ($Keys as $Index => $Value) {
      if (strlen($Value) < $MinKeyLength)
        unset($Keys[$Index]);
    }

    return array_unique($Keys);
  }

  /**
   * Function recieves possible denominators and sentences to find
   * out if they can be used to create a group of sentences that is
   * equal or greater to the $MinimumGroupSize
   *
   * @param array $Denominators     An array of possible denominators
   * @param array $List             An array of sentences
   * @param int   $MinimumGroupSize The minimum size of a valid group
   *
   * @see					autoGroupSentence
   *
   * @return array An array of unique denominators that can create a valid group
   */
  private function _getValidKeys($Denominators, $List, $MinimumGroupSize = 2) {
    $GroupCounter = 0;
    $ValidKeys = Array();

    foreach ($Denominators AS $Key) {
      foreach ($List AS $Sentence) {
        if (strpos($Sentence, $Key) !== FALSE)
          $GroupCounter++;
      }

      if ($GroupCounter >= $MinimumGroupSize) {
        $ValidKeys[] = $Key;
      }

      $GroupCounter = 0;
    }
    return array_unique($ValidKeys);
  }

  /**
   * Finds the longest string('s) in an array of strings
   *
   * @param array $Denominator and array of possible denominators
   *
   * @see          autoGroupSentence
   *
   * @return array An array of the longest denominator('s) string('s)
   */
  private function _getLongestKeys($Denominator) {
    $ValidKeyLength = 0;
    $LongesteDenominators = Array();

    foreach ($Denominator as $Key) {
      if (strlen($Key) >= $ValidKeyLength) {
        $ValidKeyLength = strlen($Key);
      }
    }

    foreach ($Denominator as $Key) {
      if (strlen($Key) == $ValidKeyLength)
        $LongesteDenominators[] = trim($Key);
    }

    return $LongesteDenominators;
  }

  /**
   * Finds the group corrosponding to the supplied denominators
   *
   * @param array $Denominators An array of denominators
   * @param array $SentenceList An array of sentences to be grouped
   *
   * @see         autoGroupSentence
   *
   * @return array Multidimensional array of sentence groups
   */
  private function _getGroup($Denominators, $SentenceList) {
    $Group = Array();

    foreach ($Denominators as $Key) {
      $CurrentGroup = Array();
      foreach ($SentenceList as $SentenceIndex => $Sentence) {
        if (strpos($Sentence, $Key) !== FALSE)
          //$CurrentGroup[$SentenceIndex] = trim(substr($Sentence, strlen($Key)));
          $CurrentGroup[$SentenceIndex] = $Sentence;
      }
      $Group[$Key] = $CurrentGroup;
    }
    return $Group;
  }

  /**
   * Extracts sentence keys of grouped sentences
   *
   * @param array $List Multidimensional array of grouped sentences
   *
   * @see autoGroupSentence
   *
   * @return array Returns found keys
   */
  private function _getExclusions($List) {
    $ExcludeList = Array();

    foreach ($List AS $GroupArray) {
      foreach ($GroupArray AS $CurrentGroup) {
        foreach ($CurrentGroup as $Index => $Element) {
          $ExcludeList[] = $Index;
        }
      }
    }
    return $ExcludeList;
  }

  /**
   * Removes elements from a List of sentencs
   *
   * @param array $List       An array of sentences
   * @param array $Exclusions An array of keys to be removed
   *
   * @see autoGroupSentence
   *
   * @return array An array stripped of the values defined in the $Exclusions array
   */
  private function _getNewList($List, $Exclusions) {
    foreach ($Exclusions as $Element) {
      if (isset($List[$Element])) {
        unset($List[$Element]);
      }
    }

    return $List;
  }

  /**
   * Adds ungroupable elements to the Groups array
   *
   * @param array $List            A complete array of original sentences
   * @param array $Groups          A multidimensional array of grouped sentences
   * @param array $RemovedElements A list of ungroupable elements to be added at the end of the $Groups array
   *
   * @see	autoGroupSentence
   *
   * @return array A multidimentional array comprised of grouped and possibly ungroupable sentences
   */
  private function _addUngroupable($List, $Groups, $RemovedElements) {
    if (count($RemovedElements) != count($List)) {
      foreach ($List as $ListKey => $ListElement) {
        if ($List[$ListKey] != $RemovedElements[$ListKey]){
          $Groups[$ListKey] = $List[$ListKey];
        }
      }
    }

    return $Groups;
  }

  /**
   * checks if the variable $array is in fact is an array and isn't empty
   *
   * @param array $array the input variable that must be an array
   *
   * @return bool
   */
  private function checkArray( $array )
  {
    if ( is_array($array) && count($array) > 0 ){
      return TRUE;
    } else {
      return FALSE;
    }
  }
}

?>