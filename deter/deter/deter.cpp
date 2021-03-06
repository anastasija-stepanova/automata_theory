// deter.cpp : Этот файл содержит функцию "main". Здесь начинается и заканчивается выполнение программы.
//

#include "pch.h"
#include <iostream>
#include <fstream>
#include <vector>
#include <set>
#include <string>
#include <sstream>
#include <algorithm>
#include <queue> 
#include "determinator.h"

using namespace std;

void readStartMatrix(ifstream &fIn, int numberOfInputSignals, int numberOfStates, vector<vector<vector<int>>> &matrix)
{
	string stringValue;
	matrix.resize(numberOfStates);
	for (int i = 0; i < numberOfStates; i++)
	{
		matrix[i].resize(numberOfInputSignals);
	}	

	getline(fIn, stringValue);
	for (int i = 0; i < numberOfStates; ++i) 
	{
		getline(fIn, stringValue);
		stringstream stringStreamValue(stringValue);
		string value;

		while (stringStreamValue >> value) 
		{
			int top = stoi(value);
			stringStreamValue >> value;
			int signal = stoi(value);
			matrix[i][signal].push_back(top);
			sort(matrix[i][signal].begin(), matrix[i][signal].end());
		}
	}
}

void printStartMatrix(ifstream &fIn, int numberOfInputSignals, int numberOfStates, vector <vector<vector<int>>> &matrix)
{
	for (int i = 0; i < numberOfStates; i++)
	{
		for (int j = 0; j < numberOfInputSignals; j++)
		{
			cout << matrix[i][j][0] << ' ';
		}
	}
}

void readFinalVector(ifstream &fIn, int size, vector<int> &vector)
{
	for (int i = 0; i < size; i++)
	{
		fIn >> vector[i];
	}
}

void printFinalVector(ifstream &fIn, int size, vector<int> &vector)
{
	for (int i = 0; i < size; i++)
	{
		cout << vector[i];
	}
}

vector<vector<int>> glueStrings(vector<vector<vector<int>>> &startMatrix, vector<int> &states)
{
	vector<vector<int>> newState;
	for (int i = 0; i < states.size(); ++i)
	{
		newState.insert(newState.end(), startMatrix[i].begin(), startMatrix[i].end());
	}

	for (int i = 0; i < newState.size(); ++i)
	{
		for (int j = 0; j < newState[i].size(); ++j)
		{
			cout << newState[i][j] << ' ';
		}
		cout << endl;
	}
	return newState;
}

void determination(vector<vector<vector<int>>> &startMatrix, vector<vector<int>> &finalMatrix)
{
	vector<vector<int>> queue;
	vector<vector<int>> visitedStates;
	vector<vector<int>> newState;
	queue.push_back(startMatrix[0][0]);

	for (auto item : queue)
	{
		auto value = queue.back();

		if (value.size() == 1)
		{
			finalMatrix.push_back(value);
			visitedStates.push_back(value);
		}
		else
		{
			newState = glueStrings(startMatrix, value);
			queue.push_back(newState[0]);
		}
	}
}

void printFinalStates(ofstream& out, const vector<vector<int>> &finalStates) 
{
	out << "New final states: " << endl;
	for (int i = 0; i < finalStates.size(); ++i) 
	{
		vector<int> finalState = finalStates[i];
		for (int j = 0; j < finalState.size(); ++j)
		{
			out << finalState[j];
		}
		out << endl;
	}
	out << endl;
}

void printResult(ofstream& out, map<vector<int>, vector<vector<vector<int>>>> result) 
{
	vector<vector<int>> mapKeys;
	for (map<vector<int>, vector<vector<vector<int>>>>::iterator it = result.begin(); it != result.end(); ++it) 
	{
		mapKeys.push_back(it->first);
	}

	vector<vector<vector<int>>> newStates;
	for (int i = 0; i < mapKeys.size(); i++) {
		for (int l = 0; l < mapKeys[i].size(); l++) 
		{
			out << mapKeys[i][l];
			if (l != mapKeys[i].size() - 1)
			{
				out << ", ";
			}
		}
		out << ": ";
		for (map<vector<int>, vector<vector<vector<int>>>>::iterator it = result.begin(); it != result.end(); ++it)
		{
			newStates.insert(newStates.end(), it->second.begin(), it->second.end());
		}
		for (int j = 0; j < newStates[0].size(); j++) 
		{
			for (int k = 0; k < newStates[i][j].size(); k++) 
			{
				out << newStates[i][j][k] << " ";
			}
			out << " | ";
		}
		out << endl;
	}

	out << endl;
}
 
void createVisualizationDotFile(ofstream &dotFileName, map<vector<int>, vector<vector<vector<int>>>> result, const vector<int> &finalStates)
{
	dotFileName << "digraph G{" << endl;

	vector<vector<int>> mapKeys;
	for (map<vector<int>, vector<vector<vector<int>>>>::iterator it = result.begin(); it != result.end(); ++it) 
	{
		mapKeys.push_back(it->first);
	}
	for (int i = 0; i < mapKeys.size(); i++)
	{
		dotFileName << "\"";
		int counter = 0;
		for (int l = 0; l < mapKeys[i].size(); l++)
		{
			dotFileName << mapKeys[i][l];
			
			if (l != mapKeys[i].size() - 1)
			{
				dotFileName << ", ";
			}
			counter++;
		}
		dotFileName << "\"";
		for (int k = 0; k < finalStates.size(); ++k)
		{
			if (counter == finalStates[k])
			{
				dotFileName << " [shape=box];";
			}
			else
			{
				continue;
			}
		}
		
		dotFileName << endl;
	}

	vector<vector<vector<int>>> newStates;
	for (int i = 0; i < mapKeys.size(); i++) 
	{
		for (map<vector<int>, vector<vector<vector<int>>>>::iterator it = result.begin(); it != result.end(); ++it) 
		{
			newStates.insert(newStates.end(), it->second.begin(), it->second.end());
		}
		for (int j = 0; j < newStates[0].size(); j++) 
		{
			if (newStates[i][j].begin() != newStates[i][j].end())
			{
				dotFileName << "    ";
				dotFileName << "\"";
				for (int l = 0; l < mapKeys[i].size(); l++)
				{
					dotFileName << mapKeys[i][l];
					if (l != mapKeys[i].size() - 1)
					{
						dotFileName << ", ";
					}
				}

				dotFileName << "\"";
				dotFileName << "->";
				dotFileName << "\"";
				for (int k = 0; k < newStates[i][j].size(); k++)
				{
					dotFileName << newStates[i][j][k];
					if (k != newStates[i][j].size() - 1)
					{
						dotFileName << ", ";
					}
				}
				dotFileName << "\"";
				dotFileName << " [label=" << j << ']' << endl;
			}
		
		}
	}

	dotFileName << "}";
	dotFileName.close();
}

int main()
{
	ifstream inputFile("in.txt");
	ofstream outputFile("out.txt");
	ofstream dotFileName("out.dot");

	int numberOfInputSignals = -1;
	int numberOfStates = -1;
	int numberOfFinalStates = -1;

	inputFile >> numberOfInputSignals;
	inputFile >> numberOfStates;
	inputFile >> numberOfFinalStates;

	vector<int> finalStates(numberOfFinalStates);
	readFinalVector(inputFile, numberOfFinalStates, finalStates);

	vector <vector<vector<int>>> startMatrix;
	readStartMatrix(inputFile, numberOfInputSignals, numberOfStates, startMatrix);

	determinator determinator(startMatrix, finalStates);
	determinator.determinate();

	vector<vector<int>> newFinalStates = determinator.getNewFinalStates();
	map<vector<int>, vector<vector<vector<int>>>> result = determinator.getDeterminationResult();
	printResult(outputFile, result);
	printFinalStates(outputFile, newFinalStates);
	outputFile << "number of input signals: " << numberOfInputSignals << endl;
	outputFile << "number of states: " << result.size() << endl;
	outputFile << "number of final states:" << numberOfFinalStates << endl;

	createVisualizationDotFile(dotFileName, result, finalStates);
}